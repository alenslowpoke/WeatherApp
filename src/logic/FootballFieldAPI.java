
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nabilfadjar
 */
public class FootballFieldAPI {

    private ArrayList<StadiumLocation> FootballFieldList;

    public FootballFieldAPI() {
        FootballFieldList = new ArrayList<>();
    }

    public void updateFootballFields() {
        String csvFileStrFormat = getFootballFieldListAsString();
        //System.out.println(str);
        String[] strSplit = csvFileStrFormat.split("\n");
        for (int i = 1; i < strSplit.length; i++) {
            String[] rowSplit = strSplit[i].split(",");
            FootballFieldList.add(new StadiumLocation(rowSplit[0].replace("\"", "").replace("'","\'"), rowSplit[1].replace("\"", "").replace("'","\'")));
        }
    }

    public void updateFootballFieldsDatabase() {
        Database fbDB = new Database();
        fbDB.populateFootballFieldTable(FootballFieldList);
        //fbDB.listFootballFieldTable();
    }

    private String getFootballFieldListAsString() {
        try {
            /*
             Adapted from: http://stackoverflow.com/questions/1381617/simplest-way-to-correctly-load-html-from-web-page-into-a-string-in-java
             Answer provided by: erickson
             */
            URL url = new URL("http://www.doogal.co.uk/FootballStadiumsCSV.php");
            URLConnection con = url.openConnection();
            Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
            Matcher m = p.matcher(con.getContentType());
            /* If Content-Type doesn't match this pre-conception, choose default and 
             * hope for the best. */
            String charset = m.matches() ? m.group(1) : "ISO-8859-1";
            Reader r = new InputStreamReader(con.getInputStream(), charset);
            StringBuilder buf = new StringBuilder();
            while (true) {
                int ch = r.read();
                if (ch < 0) {
                    break;
                }
                buf.append((char) ch);
            }
            String str = buf.toString();

            return (str);
        } catch (Exception e) {
            System.err.println("Football Field List Exception: " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        FootballFieldAPI test1 = new FootballFieldAPI();
        test1.updateFootballFields();
        test1.updateFootballFieldsDatabase();
    }
}
