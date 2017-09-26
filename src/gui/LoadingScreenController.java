package gui;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoadingScreenController {
    
    @FXML private ProgressBar loadProgressBar;
    @FXML private ImageView logoImage;
    
    private TimerTask task;
    private Timer timer;
    
    public LoadingScreenController() {
        Platform.runLater(() -> {
            loadProgressBar.setProgress(0.0);
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    if (loadProgressBar.getProgress() < 1.1) {
                        loadProgressBar.setProgress(loadProgressBar.getProgress() + 0.1);
                    } else {
                        cancel();
                        timer.cancel();
                        Platform.runLater(() -> {
                            try {
                                loadHomeScreen();
                            } catch (IOException ex) {
                                Logger.getLogger(LoadingScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                }
            };         
            timer.schedule(task, 0,  1 * 250);
        });
    }
    
    public void loadHomeScreen() throws IOException {
        task.cancel();
        timer.cancel();
        Stage stage = (Stage) loadProgressBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        stage.setScene(new Scene(root));
    }
}
