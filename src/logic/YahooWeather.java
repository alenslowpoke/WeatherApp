/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;



/**
 *
 * @author nabilfadjar
 */
public class YahooWeather {

    private String hiTemp;
    private String lowTemp;
    private String Date;
    private String stadiumName;
    private String weatherCode;

    public YahooWeather(String stadiumName, String Date, String hiTemp, String lowTemp, String weatherCode) {
        this.hiTemp=hiTemp;
        this.lowTemp=lowTemp;
        this.Date=Date;
        this.stadiumName=stadiumName;
        this.weatherCode=weatherCode;
    }

    /**
     * @return the hiTemp
     */
    public String getHiTemp() {
        return hiTemp;
    }

    /**
     * @param hiTemp the hiTemp to set
     */
    public void setHiTemp(String hiTemp) {
        this.hiTemp = hiTemp;
    }

    /**
     * @return the lowTemp
     */
    public String getLowTemp() {
        return lowTemp;
    }

    /**
     * @param lowTemp the lowTemp to set
     */
    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    /**
     * @return the Date
     */
    public String getDate() {
        return Date;
    }

    /**
     * @param Date the Date to set
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     * @return the woeid
     */
    public String getStadiumName() {
        return stadiumName;
    }

    /**
     * @param woeid the woeid to set
     */
    public void setStadiumName(String woeid) {
        this.stadiumName = woeid;
    }

    /**
     * @return the weatherCode
     */
    public String getWeatherCode() {
        return weatherCode;
    }

    /**
     * @param weatherCode the weatherCode to set
     */
    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
}
