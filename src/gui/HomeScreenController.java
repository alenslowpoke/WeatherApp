package gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import logic.CurrentLocation;
import logic.Weather;
import logic.WeatherData;
import logic.WeatherDatabase;
import logic.buildWeather;

public class HomeScreenController {
    
    private String location;
    
    private WeatherData WeatherAPI;

    @FXML
    private Label locationLabel;
    @FXML
    private Label tempLabel;
    @FXML
    private Label todayMaxLabel;
    @FXML
    private Label todayMinLabel;
    @FXML
    private Slider timeSlider;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private ImageView weatherCondIcon;
    
    private Timer timer;
    private TimerTask task;
    
    private WeatherDatabase weatherDatabase;
    
    public HomeScreenController() {
        Platform.runLater(() -> {
            location = CurrentLocation.location;
            weatherDatabase = new WeatherDatabase();
            timeSlider.setValue(0);
            timeSlider.setMin(0);
            timeSlider.setMax(1);
            timeSlider.setMajorTickUnit(1);
            timeSlider.setMinorTickCount(0);
            timeSlider.setBlockIncrement(1);
            timeSlider.setSnapToTicks(true);
            
            //WEATHER API WORK: TEAM LEADER YOYOYOYOY
            // SCHEDULER UPDATE WEATHER EVERY HOUR (CAN Be CHANGED VALUE IN MILLISCOENDS!!)
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        updateWeatherData();
                    });
                }
            };
            timer.schedule(task, 0, 60 * 60 * 1000);
            
            timeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (timeSlider.getValue() != 0) {
                    task.cancel();
                    timer.cancel();
                    try {
                        Stage stage = (Stage) timeSlider.getScene().getWindow();
                        Parent root;
                        root = FXMLLoader.load(getClass().getResource("WeekScreen.fxml"));
                        stage.setScene(new Scene(root));
                    } catch (IOException ex) {
                        Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            Stage stage = (Stage) timeSlider.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    task.cancel();
                    timer.cancel();
                }                
            });
        });       
    }
    
    public void updateWeatherData() {
        ArrayList<Weather> weather = weatherDatabase.getWeatherByLocation(location);
        long currentTime = Calendar.getInstance().getTime().getTime();
        long difference = Math.abs(currentTime - weather.get(0).getWeatherDate().getTime());
        int closestIndex = 0;
        for (int i = 1; i < weather.size(); i++) {
            long weatherTimeDiff = currentTime - weather.get(i).getWeatherDate().getTime();
            if (weatherTimeDiff < difference) {
                difference = weatherTimeDiff;
                closestIndex = i;
            }
        }
        locationLabel.setText(location);
        tempLabel.setText(String.valueOf(weather.get(closestIndex).getTemperature()) + "\u00B0C");
        //conditionLabel.setText(WeatherAPI.getCurrentConditions());
        //tomorrowLabel.setText(String.valueOf(WeatherAPI.getTempForDay(null)));
        //MAGIC ABOUT TO HAPPEN
        todayMaxLabel.setText("Max: " + String.valueOf(weather.get(closestIndex).getTemperature() + 3) + "\u00B0C");
        todayMinLabel.setText("Min: " + String.valueOf(weather.get(closestIndex).getTemperature() - 2) + "\u00B0C");
        weatherCondIcon.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get(closestIndex).getWeatherCode()).getPath()));
        backgroundImage.setImage(new Image(ShowingIcon.getWeatherBackground(weather.get(closestIndex).getWeatherCode()).getPath()));
    }
    
    @FXML protected void stadiumButton() {
        task.cancel();
        timer.cancel();
        try {
            Stage stage = (Stage) timeSlider.getScene().getWindow();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("StadiumScreen.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshWeather(ActionEvent event) {
        buildWeather bw = new buildWeather();
        bw.buildDailyWeatherForecast();
    }
}
