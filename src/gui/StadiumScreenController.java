package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import logic.CurrentLocation;
import logic.Database;

public class StadiumScreenController {
    
    @FXML private ListView<String> stadiumList;
    private Database database;
    
    public StadiumScreenController() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {            
                //ENTER LIST ITEMS TEAMLEDAER YOYOYO
                database = new Database();
                ArrayList<String[]> loadStadiums = database.getFootballFieldsAndWoeid();
                ArrayList<String> stadiums = new ArrayList<String>();
                for (int i = 0; i < loadStadiums.size(); i++)
                    stadiums.add(loadStadiums.get(i)[0]);
                    
                ObservableList<String> items = FXCollections.observableArrayList(stadiums);
                stadiumList.setItems(items);
                stadiumList.scrollTo(0);
                stadiumList.getSelectionModel().select(0);
                stadiumList.getFocusModel().focus(0);
            }
        });
    }
    
    @FXML protected void okButton() {
        try {
            CurrentLocation.location = stadiumList.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) stadiumList.getScene().getWindow();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
