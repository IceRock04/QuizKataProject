package src;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.category.CategoriesAPILoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * This is the controller for the Quiz Creator scene
 * The user is able to select a category and difficulty from a choice box, and a number of questions from a text input
 * An error message will pop up if the selected number of questions is unavailable (Ex: User selects 50 questions for a Category/Difficulty selection that only has 20 questions available)
 *
 * Problem 1
 * There was a bug that occurred that made the category "Entertainment: Cartoon & Animations" appear first in the categoryHashMap
 * This bug made it so that that specific category would have the wrong ID in the Category Choice Box
 * I had to make a slight round-about solution to it, but the issue is now fixed
 */

public class QuizCreatorController {

    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private ChoiceBox difficultyChoiceBox;
    @FXML
    private ChoiceBox typeChoiceBox;
    @FXML
    private ChoiceBox amountChoiceBox;
    @FXML
    private Button generateQuizButton;
    @FXML
    private Label errorText;
    private HashMap<Integer, String> categoryHashMap;

    private HashMap<Integer, String> difficultyHashMap;

    private int chosenId;
    private String chosenDifficulty;

    public void handleQuizGeneration(ActionEvent actionEvent) {

    }

    public void initialize() {
        try {
            //Creates a URL for an API Call that will grab all the available Categories and their ID's
            URL url = new URL("https://opentdb.com/api_category.php");

            //Opens the connection. May throw IOException
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Sets the request method, which in this case is "GET"
            connection.setRequestMethod("GET");

            //Check to see if the connection has a valid response code (200) before proceeding
            if (connection.getResponseCode() == 200) {
                //Creates a BufferedReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //Creates a StringBuilder
                StringBuilder response = new StringBuilder();
                String text;
                //Reads in the data from the API Call and puts it in a StringBuilder
                while ((text = reader.readLine()) != null) {
                    response.append(text);
                }
                //Closes the BufferedReader
                reader.close();

                //Creates a Gson object
                Gson gson = new Gson();

                //Creates a QuestionAPILoader object, whose job is to read in the text from the API call
                CategoriesAPILoader loader = gson.fromJson(String.valueOf(response), CategoriesAPILoader.class);

                //Gets a HashMap of ID's and Category Names from the CategoriesAPILoader
                categoryHashMap = loader.getCategoryMap();

                //The next couple of lines are to sort the Key Set for the Category Hash Map (*Problem 1)
                //Creating an ArrayList to house all the Keys from the Category Hash Map
                ArrayList<Integer> keyNumbers = new ArrayList<>();
                //Filling the ArrayList with values
                for (Map.Entry<Integer, String> set: categoryHashMap.entrySet()) {
                    keyNumbers.add(set.getKey());
                }
                //Sorting the Keys in the Hash Map
                Collections.sort(keyNumbers);
                //Filling the Category Choice Box with items
                for (Integer i: keyNumbers) {
                    categoryChoiceBox.getItems().add(categoryHashMap.get(i));
                }

            }
            //Disconnect from the connection
            connection.disconnect();

            //Setting up listener event for the Category Choice Box
            categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> {
                //An offset of 9 is provided since the id's for the categories start at 9
                chosenId = newValue.intValue() + 9;
                System.out.println(categoryHashMap.get(chosenId));
            });

            //Creating the Difficulty Hash Map
            difficultyHashMap = new HashMap<>();

            //Filling the Difficulty Hash Map with values
            difficultyHashMap.put(0, "Easy");
            difficultyHashMap.put(1, "Medium");
            difficultyHashMap.put(2, "Hard");

            //Filling the Difficulty Choice Box with items
            for (Map.Entry<Integer, String> set: difficultyHashMap.entrySet()) {
                difficultyChoiceBox.getItems().add(set.getValue());
            }

            //Setting up listener event for the Difficulty Choice Box
            difficultyChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> {
                chosenDifficulty = difficultyHashMap.get(newValue.intValue()).toLowerCase();
                System.out.println(chosenDifficulty);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
