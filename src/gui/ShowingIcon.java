package gui;

public class ShowingIcon {
    public static WeatherIcon getWeatherIcon(int code) {
            if (code >= 0 && code <= 12)
                    return WeatherIcon.RAINY_ICON;
            if (code > 12 && code <= 18)
                    return WeatherIcon.SNOWING_ICON;
            if (code > 18 && code <= 30)
                    return WeatherIcon.CLOUDY_ICON;
            if (code > 30 && code <= 34)
                    return WeatherIcon.SUNNY_ICON;
            if (code > 34 && code <= 35)
                    return WeatherIcon.RAINY_ICON;
            if (code > 35 && code <= 36)
                    return WeatherIcon.SUNNY_ICON;
            if (code > 36 && code <= 40)
                    return WeatherIcon.RAINY_ICON;
            if (code > 40 && code <= 43)
                    return WeatherIcon.SNOWING_ICON;
            if (code > 43 && code <= 44)
                    return WeatherIcon.CLOUDY_ICON;
            if (code > 44 && code <= 45)
                    return WeatherIcon.RAINY_ICON;
            if (code > 45 && code <= 46)
                    return WeatherIcon.SNOWING_ICON;
            if (code > 46 && code <= 47)
                    return WeatherIcon.RAINY_ICON;
            if (code == 3200)
                    return WeatherIcon.SUNNY_ICON;
            return null;
    }
    
    public static WeatherBackground getWeatherBackground(int code) {
            if (code >= 0 && code <= 12)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code > 12 && code <= 18)
                    return WeatherBackground.SNOWING_BACKGROUND;
            if (code > 18 && code <= 30)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code > 30 && code <= 34)
                    return WeatherBackground.SUNNY_BACKGROUND;
            if (code > 34 && code <= 35)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code > 35 && code <= 36)
                    return WeatherBackground.SUNNY_BACKGROUND;
            if (code > 36 && code <= 40)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code > 40 && code <= 43)
                    return WeatherBackground.SNOWING_BACKGROUND;
            if (code > 43 && code <= 44)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code > 44 && code <= 45)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code > 45 && code <= 46)
                    return WeatherBackground.SNOWING_BACKGROUND;
            if (code > 46 && code <= 47)
                    return WeatherBackground.RAINY_BACKGROUND;
            if (code == 3200)
                    return WeatherBackground.SUNNY_BACKGROUND;
            return null;
    }
}