/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 *
 * @author nabilfadjar
 */
public class buildWeather {

    private WeatherAPI getWeatherYahoo;
    private FootballFieldAPI initFootballFields;
    private Database weatherDB = new Database();

    public void buildDailyWeatherForecast() {
        initFootballFields = new FootballFieldAPI();
        initFootballFields.updateFootballFields();
        initFootballFields.updateFootballFieldsDatabase();

        ArrayList<String[]> footballStList = weatherDB.getFootballFieldsAndWoeid();
        ArrayList<Weather> allWeather = new ArrayList<>();
        for (String[] footballStList1 : footballStList) {
            getWeatherYahoo = new WeatherAPI(footballStList1[1], footballStList1[0]);
            getWeatherYahoo.getWeatherFromYahoo();
            allWeather.addAll(getWeatherYahoo.generateWeatherForecastPerDay());
        }
        
        WeatherDatabase dbTest = new WeatherDatabase();
        dbTest.storeWeather(allWeather);
        
        //for(Weather eachHr : allWeather)
        //System.out.println(eachHr.getDate()+" "+eachHr.getTemperature() +" "+eachHr.getWeatherCode() +" "+ eachHr.getWeatherLocation());
    }
    
    public static void main(String[] args){
        buildWeather test = new buildWeather();
        test.buildDailyWeatherForecast();
    }
}

