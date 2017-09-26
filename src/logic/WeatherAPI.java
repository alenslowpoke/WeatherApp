package logic;

import java.net.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherAPI {

    private String woeid;
    private String stadiumName;
    private String weatherHTML;
    private ArrayList<YahooWeather> yahooWeatherForecastList;

    public WeatherAPI(String woeid, String stadiumName) {
        this.woeid = woeid;
        this.stadiumName = stadiumName;
    }

    public void getWeatherFromYahoo() {
        getWeatherAsRSS();
        parseWeather();
    }

    private void parseWeather() {
        yahooWeatherForecastList = new ArrayList<>();
        int startIndex = 0;
        while (startIndex != -1) {
            startIndex = weatherHTML.indexOf("<yweather:forecast", startIndex);
            if (startIndex != -1) { // found a weather forecast
                int endIndex = weatherHTML.indexOf(">", startIndex);
                String weatherForecast = weatherHTML.substring(startIndex, endIndex + 1);

                // Get Data from Forecast				
                String lowTemp = getValueForKey(weatherForecast, "low");
                String highTemp = getValueForKey(weatherForecast, "high");
                String date = getValueForKey(weatherForecast, "date");
                String code = getValueForKey(weatherForecast, "code");

                //System.out.println(lowTemp + " " + highTemp + " " + date + " " + code);
                //Store in temp ArrayList
                yahooWeatherForecastList.add(new YahooWeather(this.stadiumName, date, lowTemp, highTemp, code));

                // move to end of this forecast
                startIndex = endIndex;
            }
        }
    }

    private String getValueForKey(String theString, String keyString) {
        int startIndex = theString.indexOf(keyString);
        startIndex = theString.indexOf("\"", startIndex);
        int endIndex = theString.indexOf("\"", startIndex + 1);
        String resultString = theString.substring(startIndex + 1, endIndex);
        return resultString;
    }

    private void getWeatherAsRSS() {
        try {
            /*
             Adapted from: http://stackoverflow.com/questions/1381617/simplest-way-to-correctly-load-html-from-web-page-into-a-string-in-java
             Answer provided by: erickson
             */
            URL url = new URL("http://weather.yahooapis.com/forecastrss?w=" + woeid + "&u=c");
            URLConnection con = url.openConnection();
            Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
            Matcher m = p.matcher(con.getContentType());
            /* If Content-Type doesn't match this pre-conception, choose default and 
             * hope for the best. */
            String charset = m.matches() ? m.group(1) : "ISO-8859-1";
            Reader r = new InputStreamReader(con.getInputStream(), charset);
            StringBuilder buf = new StringBuilder();
            while (true) {
                int ch = r.read();
                if (ch < 0) {
                    break;
                }
                buf.append((char) ch);
            }
            weatherHTML = buf.toString();
        } catch (Exception e) {
            System.err.println("Weather API Exception: " + e);
        }
    }
    
    public ArrayList<Weather> generateWeatherForecastPerDay(){
        ArrayList<Weather> allForecatedDays = new ArrayList<Weather>();
        for(int i=0; i<yahooWeatherForecastList.size(); i++){
            try {
                allForecatedDays.addAll(generateWeatherHourlyForecast(yahooWeatherForecastList.get(i)));
            } catch (ParseException ex) {
                Logger.getLogger(WeatherAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allForecatedDays;
    }
    
    public ArrayList<Weather> generateWeatherHourlyForecast(YahooWeather eachDay) throws ParseException {
        String weatherCode = eachDay.getWeatherCode();
        String weatherLocation = eachDay.getStadiumName();
        String loTemp = eachDay.getLowTemp();
        String hiTemp = eachDay.getHiTemp();
        String weatherDate = eachDay.getDate();

        //Create Weather Class and Handle Location and Code
        Weather[] weatherGen = new Weather[24];
        for (int time = 0; time < 24; time++) {
            weatherGen[time] = new Weather();
            weatherGen[time].setWeatherCode(Integer.parseInt(weatherCode));
            weatherGen[time].setWeatherLocation(weatherLocation);
        }

        //Handle Date
        //yahooWeatherForecastList.get(0);
        String date = weatherDate;
        for (int time = 0; time < 24; time++) {
            String dateTimeFormat = date + " " + time;
            DateFormat format = new SimpleDateFormat("d MMM yyyy H", Locale.ENGLISH);
            Date weatherDateAndTime = format.parse(dateTimeFormat);

            weatherGen[time].setWeatherDate(weatherDateAndTime);
            //System.out.println(weatherDateAndTime);
        }

        /*
         //Handle Temp
         int[] temperature = new int[24];        
         int low=3;
         int hi=11;
         int currentTemp=0;
         if((low+hi)%2!=0) 
         currentTemp=(low+hi+1)/2;
         else 
         currentTemp = (low+hi)/2;
         temperature[0]=low;
         for (int time = 1; time < 11; time++) {        
         temperature[time]=temperature[time-1]+1;
         if(temperature[time]>manipTemp(hi,currentTemp)) temperature[time]-=2;
         }
         temperature[11]=manipTemp(hi,currentTemp);
         temperature[12]=manipTemp(hi,currentTemp);
         temperature[13]=manipTemp(hi,currentTemp);
         for (int time = 14; time < 23; time++) {
         temperature[time]=temperature[time-1]-1;
         if(temperature[time]<low) temperature[time]+=2;
         }
         temperature[23]=low;       
         System.out.println(Arrays.toString(temperature));
         */
        //Handle Temp       
        int low = Integer.parseInt(loTemp);
        int hi = Integer.parseInt(hiTemp);
        int currentTemp = 0;
        if ((low + hi) % 2 != 0) {
            currentTemp = (low + hi + 1) / 2;
        } else {
            currentTemp = (low + hi) / 2;
        }
        weatherGen[0].setTemperature(low);
        for (int time = 1; time < 11; time++) {
            weatherGen[time].setTemperature(weatherGen[time - 1].getTemperature() + 1);
            if (weatherGen[time].getTemperature() > manipTemp(hi, currentTemp)) {
                weatherGen[time].setTemperature(weatherGen[time].getTemperature() - 2);
            }
        }
        weatherGen[11].setTemperature(manipTemp(hi, currentTemp));
        weatherGen[12].setTemperature(manipTemp(hi, currentTemp));
        weatherGen[13].setTemperature(manipTemp(hi, currentTemp));
        for (int time = 14; time < 23; time++) {
            weatherGen[time].setTemperature(weatherGen[time - 1].getTemperature() - 1);
            if (weatherGen[time].getTemperature() < low) {
                weatherGen[time].setTemperature(weatherGen[time].getTemperature() + 2);
            }
        }
        weatherGen[23].setTemperature(low);

        String tempW = "";
        for (Weather weatherGen1 : weatherGen) {
            tempW += ("," + weatherGen1.getTemperature());
        }

        ArrayList<Weather> weatherEachHrList = new ArrayList<>();
        //System.out.println(tempW);
        for(Weather eachHr : weatherGen){
            weatherEachHrList.add(eachHr);
        }
        
        return weatherEachHrList;
    }

    private int manipTemp(int temp1, int temp2) {
        Random numRand = new Random();
        return ((numRand.nextInt() % 2 == 0) ? ((numRand.nextInt() % 2 == 0) ? temp1 - 2 : temp2) : ((numRand.nextInt() % 2 == 0) ? temp1 : temp2));
    }

    public static void main(String[] args) {
        WeatherAPI test = new WeatherAPI("91940033","This Location");
        test.getWeatherFromYahoo();
    }
}
