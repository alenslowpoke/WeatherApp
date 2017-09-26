package gui;

public enum WeatherBackground {
    
    SUNNY_BACKGROUND("./resources/footballBackgroundSunny.png"),
    RAINY_BACKGROUND("./resources/footballBackgroundRain.png"),
    SNOWING_BACKGROUND("./resources/footballBackgroundSnow.png");
    
    private final String path;

    WeatherBackground(String path) {
        this.path = path;
    }

    String getPath() {
        return path;
    }
}
