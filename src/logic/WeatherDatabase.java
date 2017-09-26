/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnf30
 */
public class WeatherDatabase extends Database {

    public void storeWeather(ArrayList<Weather> eachHrWeather) {
        //Reset old table
        resetWeatherTable();
        String query = "INSERT INTO Weather (Location,Temperature,Code,DateTime) VALUES(?,?,?,?);";

        openConnection();
        PreparedStatement stmtPS = null;
        try {
            for (int i = 0; i < eachHrWeather.size(); i++) {
                stmtPS = getDbConn().prepareStatement(query);

                String wthLoc = eachHrWeather.get(i).getWeatherLocation();
                String wthDate = eachHrWeather.get(i).getWeatherDate().toString();
                int wthTemp = eachHrWeather.get(i).getTemperature();
                int wthCode = eachHrWeather.get(i).getWeatherCode();

                stmtPS.setString(1, wthLoc);
                stmtPS.setString(4, wthDate);
                stmtPS.setInt(2, wthTemp);
                stmtPS.setInt(3, wthCode);

                stmtPS.executeUpdate();
                stmtPS.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        closeConnection();
    }

    public void resetWeatherTable() {
        openConnection();
        Statement stmt = null;
        try {
            stmt = getDbConn().createStatement();
            stmt.executeUpdate("DELETE FROM Weather");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Weather';");
            stmt.executeUpdate("VACUUM");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();
    }

    public ArrayList<Weather> getWeatherByLocation(String loc) {
        ArrayList<Weather> weatherDay = new ArrayList<>();
        openConnection();
        Statement stmt = null;
        try {
            stmt = getDbConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Weather where Location='"+loc+"';");
            while (rs.next()) {
                String wthLoc = rs.getString("Location");
                String wthDate = rs.getString("DateTime");
                int wthTemp = rs.getInt("Temperature");
                int wthCode = rs.getInt("Code");
                
                weatherDay.add(new Weather(wthTemp,wthCode,wthDate,wthLoc));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        closeConnection();
        
        return weatherDay;

    }
}
