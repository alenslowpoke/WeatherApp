package logic;

import gui.WeatherBackground;
import gui.WeatherIcon;

public class WeatherData {
    
    public WeatherData() {
        
    }
    
    public String getCurrentLocation() {
        return null;
    }
    
    public int getCurrentTemp() {
        return 0;
    }
    
    public String getCurrentConditions() {
        return null;
    }
    
    public WeatherIcon getCurrentConditionsIcon() {
        return WeatherIcon.SUNNY_ICON;
    }
    
    public WeatherBackground getCurrentConditionsBackground() {
        return WeatherBackground.SUNNY_BACKGROUND;
    }
    
    public int getTodayMax() {
        return 0;
    }
    
    public int getTodayMin() {
        return 0;
    }
    
    public String getWeatherDay(String date) {
        return null;
    }
    
    public int getTempForDay(String date) {
        return 0;
    }
    
    public WeatherIcon getDayConditionsIcon(String date) {
        return WeatherIcon.SUNNY_ICON;
    }
    
}
