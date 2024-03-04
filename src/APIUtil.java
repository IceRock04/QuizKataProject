/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is meant to parse through API calls and return the response from them
 * Like the Quiz class, this APIUtil class was turned into a Singleton for ease of use throughout multiple controllers
 */

public class APIUtil {

    private static APIUtil apiUtilInstance = null;
    private APIUtil() {

    }

    /**
     * A static method that uses the Singleton pattern to ensure that there is only one instance of the APIUtil class
     * @return an instance of APIUtil
     */
    public static APIUtil APIUtil() {
        if (apiUtilInstance == null) {
            apiUtilInstance = new APIUtil();
        }
        return apiUtilInstance;
    }

    /**
     * This method takes in a given url and returns the response from the API Call
     * @param urlText represents the provided url
     * @return a string representing the response from the API Call
     * @throws RuntimeException if the API call could not be made
     */
    public String parseAPICall(String urlText) {
        String result;
        StringBuilder response;
        try {
            //Generate the url for the given API call
            URL url = new URL(urlText);

            //Opens the connection. May throw IOException
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Sets the request method, which in this case is "GET"
            connection.setRequestMethod("GET");

            //Check to see if the connection has a valid response code (200) before proceeding
            if (connection.getResponseCode() == 200) {
                //Creates a BufferedReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //Creates a StringBuilder
                response = new StringBuilder();
                String text;
                //Reads in the data from the API Call and puts it in a StringBuilder
                while ((text = reader.readLine()) != null) {
                    response.append(text);
                }
                //Closes the BufferedReader
                reader.close();

                //Disconnect from the connection
                connection.disconnect();

                //Returns the API data
                result = response.toString();
            } else {
                //Disconnect from the connection
                connection.disconnect();

                //Returns blank, as at this point the API call was unsuccessful
                result = "";
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("The given url is malformed. Please reenter the url");
        } catch (IOException e) {
            throw new RuntimeException("The API Call could not be made");
        }
        return result;
    }
}
