/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nabilfadjar
 */
public class Database {

    private Connection dbConn;

    public void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConn = DriverManager.getConnection("jdbc:sqlite:db/weatherAppDB.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void closeConnection() {
        try {
            getDbConn().close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the dbConn
     */
    public Connection getDbConn() {
        return dbConn;
    }

    public void populateFootballFieldTable(ArrayList<StadiumLocation> stadiums) {
        //Reset old table
        resetFootballFieldTable();
        String query = "INSERT INTO Stadium (StadiumName,StadiumTeam,StadiumWOEID) VALUES(?, ?, ?);";

        openConnection();
        PreparedStatement stmtPS = null;
        try {
            for (int i = 0; i < stadiums.size(); i++) {
                stmtPS = getDbConn().prepareStatement(query);
                
                String stName = stadiums.get(i).getStadiumName();
                String stTeam = stadiums.get(i).getFootballTeam();
                String stWoeid = stadiums.get(i).getWoeid();
                
                if(stWoeid.equals("0")) continue; //Ignore blank woeids
                
                stmtPS.setString(1, stName);
                stmtPS.setString(2, stTeam);
                stmtPS.setString(3, stWoeid);
                stmtPS.executeUpdate();
                stmtPS.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        closeConnection();
    }

    public void listFootballFieldTable() {
        openConnection();
        Statement stmt = null;
        try {
            stmt = getDbConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Stadium;");
            while (rs.next()) {
                String stName = rs.getString("StadiumName");
                String stTeam = rs.getString("StadiumTeam");
                String stWoeid = rs.getString("StadiumWOEID");

                System.out.println("Stadium Name = " + stName);
                System.out.println("Stadium Team = " + stTeam);
                System.out.println("Stadium WOEID = " + stWoeid);
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        closeConnection();
    }

    public ArrayList<String[]> getFootballFieldsAndWoeid() {
        openConnection();
        Statement stmt = null;
        ArrayList<String[]> stList = new ArrayList<>();        
        
        try {
            stmt = getDbConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT StadiumName,StadiumWOEID FROM Stadium;");
            while (rs.next()) {
                String[] locNwoeid = new String[2];
                locNwoeid[0]=rs.getString("StadiumName");
                locNwoeid[1]=rs.getString("StadiumWOEID");
                stList.add(locNwoeid);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        closeConnection();
        return stList;
    }
    
    public void resetFootballFieldTable() {
        openConnection();
        Statement stmt = null;
        try {
            stmt = getDbConn().createStatement();
            stmt.executeUpdate("DELETE FROM Stadium");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Stadium';");
            stmt.executeUpdate("VACUUM");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();
    }
    
    public ArrayList<String> getStadiumFromWoeid(String Woeid){
        openConnection();
        Statement stmt = null;
        ArrayList<String> stNames = new ArrayList<>();
        try {
            stmt = getDbConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT StadiumName FROM Stadium where StadiumWOEID="+Woeid+";");
            
            int i=0; //Temp Counter
            while (rs.next()) {
                stNames.add(rs.getString("StadiumName"));

                System.out.println("Stadium Name = " + stNames.get(i));
                i++;
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        closeConnection();
        return stNames;
    }
    
    public static void main(String[] args) {
        String lol = "\''";
        System.out.println(lol);
        ArrayList<StadiumLocation> stadiums = new ArrayList<>();
        stadiums.add(new StadiumLocation("St Mary's Stadium", "Fucktards"));
        stadiums.add(new StadiumLocation("Old Trafford", "ManU"));
        stadiums.add(new StadiumLocation("Emirates Stadium", "ManU"));

        Database testDB = new Database();
        testDB.populateFootballFieldTable(stadiums);
        testDB.listFootballFieldTable();

    }

}
