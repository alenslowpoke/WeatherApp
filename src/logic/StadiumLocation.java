/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author nabilfadjar
 */
public class StadiumLocation extends Location {

    //http://www.doogal.co.uk/FootballStadiumsCSV.php
    private String stadiumName;
    private String FootballTeam;

    public StadiumLocation(String stadiumName, String FootballTeam) {
        this.stadiumName = stadiumName;
        this.FootballTeam = FootballTeam;
        resolveWoeid(stadiumName);
    }

    /**
     * @return the stadiumName
     */
    public String getStadiumName() {
        return stadiumName;
    }

    /**
     * @param stadiumName the stadiumName to set
     */
    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
        resolveWoeid(stadiumName);

    }

    /**
     * @return the FootballTeam
     */
    public String getFootballTeam() {
        return FootballTeam;
    }

    /**
     * @param FootballTeam the FootballTeam to set
     */
    public void setFootballTeam(String FootballTeam) {
        this.FootballTeam = FootballTeam;
    }

    public static void main(String[] args) {
        StadiumLocation test = new StadiumLocation("St Mary's Stadium", "Jack FC");
    }
}
