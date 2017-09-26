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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nabilfadjar
 */
public abstract class Location {

    private String woeid;

    /**
     * @return the woeid
     */
    public String getWoeid() {
        return woeid;
    }

    /**
     * @param woeid the woeid to set
     */
    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public void resolveWoeid(String placeName) {
        //Using NABIL YAHOO Application ID Key
        //dj0yJmk9WEtidE1ySk1MVm4zJmQ9WVdrOVJVZE5SSEpwTkdNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0wMQ--
        //http://where.yahooapis.com/v1/places.q([yourplacehere])?appid=[yourappidhere]

        String appID = "dj0yJmk9WEtidE1ySk1MVm4zJmQ9WVdrOVJVZE5SSEpwTkdNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0wMQ--";
        placeName = placeName.replace(" ", "%20").replace("\'", "%27");
        String resultQuery = "";
        try {
            /*
             Adapted from: http://stackoverflow.com/questions/1381617/simplest-way-to-correctly-load-html-from-web-page-into-a-string-in-java
             Answer provided by: erickson
             */
            URL url = new URL("http://where.yahooapis.com/v1/places.q('" + placeName + "')?appid=" + appID);
            //System.out.println("http://where.yahooapis.com/v1/places.q('" + stadiumName + "')?appid=" + appID);
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
            resultQuery = buf.toString();
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }

        setWoeid(parseWoeidXML(resultQuery));
        //System.out.println(getWoeid());
    }

    private String parseWoeidXML(String resultQuery) {
        String woeidObtained = "0";
        int startIndex = -1; //temp init
        int endIndex = -1; //temp init

        /**
         * From GeoLocation Yahoo: (Start from bottom all the way up)
         *
         * <country type="Country" code="GB" woeid="23424975">United
         * Kingdom</country>
         * <admin1 type="Country" code="GB-ENG" woeid="24554868">England</admin1>
         * <admin2 type="County" code="GB-GTM" woeid="12602158">Greater
         * Manchester</admin2>
         * <admin3 type="Local Administrative Area" code="" woeid="12695841">Manchester
         * City</admin3>
         * <locality1 type="Town" woeid="28218">Manchester</locality1>
         * <locality2 type="Suburb" woeid="30986">Old Trafford</locality2>
         * <postal type="Postal Code" woeid="26349008">M16 7</postal>
         */
        int switchChange = 0;
        while (startIndex < 0 || endIndex < 0) {
            startIndex = 0;
            endIndex = 0;

            switch (switchChange) {
                case 0: //From Postal
                    //startIndex = resultQuery.indexOf("<postal type=\"Postal Code\" woeid=\"", startIndex);
                    startIndex = resultQuery.indexOf("<postal ", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                case 1: //From Locality 2                   
                    startIndex = resultQuery.indexOf("<locality2 ", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                case 2: //From Locality 1                  
                    startIndex = resultQuery.indexOf("<locality1 ", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                case 3: //From Admin 3                  
                    startIndex = resultQuery.indexOf("<admin3 ", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                case 4: //From Admin 2                
                    startIndex = resultQuery.indexOf("<admin1 ", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                case 5: //From Admin 1               
                    startIndex = resultQuery.indexOf("<country ", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                case 6: //From Country                   
                    startIndex = resultQuery.indexOf("<postal type=\"Postal Code\" woeid=\"", startIndex);
                    endIndex = resultQuery.indexOf(">", startIndex);
                    break;
                default:
                    return woeidObtained;
            }
            switchChange++;
        }

        resultQuery = resultQuery.substring(startIndex, endIndex + 1);

        //Get woeid
        startIndex = resultQuery.indexOf("woeid=\"") + 7;
        endIndex = resultQuery.indexOf("\"", startIndex);
        woeidObtained = resultQuery.substring(startIndex, endIndex + 1).replace("\"", "");

        return woeidObtained;
    }
}
