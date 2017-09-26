/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nabilfadjar
 */
public class Weather {
    private int temperature;
    private Date weatherDate;
    private String weatherLocation;
    private int weatherCode;    
    
    public Weather(){
    }
    
    public Weather(int temperature, int code, String Date, String location){
        try {
            this.temperature=temperature;
            this.weatherCode=code;
            this.weatherLocation=location;
            
            DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z YYYY", Locale.ENGLISH);
            this.weatherDate = format.parse(Date);
        } catch (ParseException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * @return the temperature
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the weatherDate
     */
    public Date getWeatherDate() {
        return weatherDate;
    }

    /**
     * @param weatherDate the weatherDate to set
     */
    public void setWeatherDate(Date weatherDate) {
        this.weatherDate = weatherDate;
    }

    /**
     * @return the weatherLocation
     */
    public String getWeatherLocation() {
        return weatherLocation;
    }

    /**
     * @param weatherLocation the weatherLocation to set
     */
    public void setWeatherLocation(String weatherLocation) {
        this.weatherLocation = weatherLocation;
    }

    /**
     * @return the weatherCode
     */
    public int getWeatherCode() {
        return weatherCode;
    }

    /**
     * @param weatherCode the weatherCode to set
     */
    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }


}
