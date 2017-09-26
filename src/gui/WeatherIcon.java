package gui;

public enum WeatherIcon {
    
    SUNNY_ICON("./resources/sunny_icon.png"),
    CLOUDY_ICON("./resources/cloudy_icon.png"),
    RAINY_ICON("./resources/rainy_icon.png"),
    SNOWING_ICON("./resources/snowing_icon.png");
    
    private final String path;
    
    WeatherIcon(String path) {
        this.path = path;
    }
    
    String getPath() {
        return path;
    }
}
