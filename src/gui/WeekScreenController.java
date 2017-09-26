package gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.CurrentLocation;
import logic.Weather;
import logic.WeatherData;
import logic.WeatherDatabase;

public class WeekScreenController {
    
    private String location;
    private WeatherDatabase weatherDatabase;

    @FXML
    private Label sunLabel;
    @FXML
    private Label monLabel;
    @FXML
    private Label tuesLabel;
    @FXML
    private Label wedLabel;
    @FXML
    private Label thursLabel;
    @FXML
    private Label friLabel;
    @FXML
    private Label satLabel;
    @FXML
    private ImageView sunImage;
    @FXML
    private ImageView monImage;
    @FXML
    private ImageView tuesImage;
    @FXML
    private ImageView wedImage;
    @FXML
    private ImageView thursImage;
    @FXML
    private ImageView weekBackgroundImage;
    @FXML
    private Slider timeSlider;
    @FXML
    private Slider weatherTimeSlider;
    
    private Timer timer;
    private TimerTask task;
    
    public WeekScreenController() {
        Platform.runLater(() -> {
            weatherDatabase = new WeatherDatabase();
            location = CurrentLocation.location;
            timeSlider.setValue(1);
            timeSlider.setMin(0);
            timeSlider.setMax(1);
            timeSlider.setMajorTickUnit(1);
            timeSlider.setMinorTickCount(0);
            timeSlider.setBlockIncrement(1);
            timeSlider.setSnapToTicks(true);
            
            weatherTimeSlider.setValue(0);
            weatherTimeSlider.setMin(0);
            weatherTimeSlider.setMax(23);
            weatherTimeSlider.setMajorTickUnit(1);
            weatherTimeSlider.setMinorTickCount(1);
            weatherTimeSlider.setBlockIncrement(1);
            weatherTimeSlider.setSnapToTicks(true);
            
            //WEATHER API WORK: TEAM LEADER YOYOYOYOY
            // SCHEDULER UPDATE WEATHER EVERY HOUR (CAN Be CHANGED VALUE IN MILLISCOENDS!!)
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        updateWeatherData(0);
                    });
                }
            };
            timer.schedule(task, 0, 60 * 60 * 1000);
            
            timeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (timeSlider.getValue() != 1) {
                    try {
                        Stage stage = (Stage) timeSlider.getScene().getWindow();
                        Parent root;
                        root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
                        stage.setScene(new Scene(root));
                    } catch (IOException ex) {
                        Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            weatherTimeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                updateWeatherData(weatherTimeSlider.getValue());
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
    
    public void updateWeatherData(double hour) {
        ArrayList<Weather> weather = weatherDatabase.getWeatherByLocation(location);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E HH:mm");
        //System.out.println(dateFormat.format(dateFormat.parse("31/05/2011")));
        sunLabel.setText(dateFormat.format(weather.get((int) hour).getWeatherDate()) + "    " + weather.get((int) hour).getTemperature() + "\u00B0C");
        monLabel.setText(dateFormat.format(weather.get((int) hour + 24).getWeatherDate()) + "    " + weather.get((int) hour + 24).getTemperature() + "\u00B0C");
        tuesLabel.setText(dateFormat.format(weather.get((int) hour + 48).getWeatherDate()) + "    " + weather.get((int) hour  + 48).getTemperature() + "\u00B0C");
        wedLabel.setText(dateFormat.format(weather.get((int) hour + 72).getWeatherDate()) + "    " + weather.get((int) hour + 72).getTemperature() + "\u00B0C");
        thursLabel.setText(dateFormat.format(weather.get((int) hour + 96).getWeatherDate()) + "    " + weather.get((int) hour + 96).getTemperature() + "\u00B0C");
        friLabel.setText("");
        satLabel.setText("");
        
        sunImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour).getWeatherCode()).getPath()));
        monImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour + 24).getWeatherCode()).getPath()));
        tuesImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour + 48).getWeatherCode()).getPath()));
        wedImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour + 72).getWeatherCode()).getPath()));
        thursImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour + 96).getWeatherCode()).getPath()));
        //friImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour).getWeatherCode()).getPath()));
        //satImage.setImage(new Image(ShowingIcon.getWeatherIcon(weather.get((int) hour).getWeatherCode()).getPath()));
        
        //weekBackgroundImage.setImage(new Image(WeatherAPI.getCurrentConditionsBackground().getPath()));
    }
}
