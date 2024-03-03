package src;

import com.google.gson.Gson;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import src.availableQuestions.AvailableQuestionsAPILoader;
import src.category.CategoriesAPILoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    private TextField numQuestionsText;
    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private ChoiceBox difficultyChoiceBox;
    @FXML
    private Button generateQuizButton;
    @FXML
    private Label errorText;
    private HashMap<Integer, String> categoryHashMap;

    private HashMap<Integer, String> difficultyHashMap;

    private int chosenId;

    private int chosenNumQuestions;
    private int chosenDifficulty;

    /**
     * This method handles the creation of a quiz.
     * It will check to see if the user put in any values for number of questions, a difficulty, or a category
     * Due to the default values, if a user does not select any options when generating a quiz, it will generate a 10 question quiz of any category and difficulty
     *
     * @param actionEvent represents the user clicking on the button
     */
    public void handleQuizGeneration(ActionEvent actionEvent) {
        //Checks if the user's desired number of questions is available
        if (validNumQuestions()) {
            //Creates a quiz
            checkingQuizCreation();
        }
    }

    /**
     * This is the initialize method for the QUiz Creator
     * It fills in default values for the available quiz creator options
     * It also fills in the options for category and difficulty selection in their respective choice boxes
     */
    public void initialize() {
        init();
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
                for (Map.Entry<Integer, String> set : categoryHashMap.entrySet()) {
                    keyNumbers.add(set.getKey());
                }
                //Sorting the Keys in the Hash Map
                Collections.sort(keyNumbers);
                //Filling the Category Choice Box with items
                for (Integer i : keyNumbers) {
                    categoryChoiceBox.getItems().add(categoryHashMap.get(i));
                }

            }
            //Disconnect from the connection
            connection.disconnect();

            //Setting up listener event for the Category Choice Box to log the users selection
            categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> {
                //An offset of 9 is provided since the id's for the categories start at 9
                chosenId = newValue.intValue() + 9;
            });

            //Creating the Difficulty Hash Map
            difficultyHashMap = new HashMap<>();

            //Filling the Difficulty Hash Map with values
            difficultyHashMap.put(0, "Easy");
            difficultyHashMap.put(1, "Medium");
            difficultyHashMap.put(2, "Hard");

            //Filling the Difficulty Choice Box with items
            for (Map.Entry<Integer, String> set : difficultyHashMap.entrySet()) {
                difficultyChoiceBox.getItems().add(set.getValue());
            }

            //Setting up listener event for the Difficulty Choice Box to log the users selection
            difficultyChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> {
                //chosenDifficulty = difficultyHashMap.get(newValue.intValue()).toLowerCase();
                chosenDifficulty = newValue.intValue();
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This is the "initialize" method for the Quiz Creator
     * It resets the quiz creator selections from the previous quiz (or instantiates them when the Quiz Creator is initially shown)
     */
    public void init() {
        //Setting default values for quiz selection
        //The default number of questions will be 10
        chosenNumQuestions = 10;
        //The chosen category ID and the chosen difficulty are set to "blank" values, which implies that they don't have a preference on category or difficulty
        chosenId = 0;
        chosenDifficulty = -1;

    }

    private boolean validNumQuestions() {
        try {
            chosenNumQuestions = Integer.parseInt(numQuestionsText.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void checkingQuizCreation() {
        String response;
        Gson gson = new Gson();
        int numQuestions;
        //Checking if a category was selected
        if (chosenId != 0) {
            //Grabs the response text from the API call
            response = getAPIResponse("https://opentdb.com/api_count.php?category=" + chosenId);

            //Creates a QuestionAPILoader object, whose job is to read in the text from the API call
            AvailableQuestionsAPILoader loader = gson.fromJson(response, AvailableQuestionsAPILoader.class);

            //Gets the number of available questions for the current category selection
            numQuestions = loader.getQuestionAmount(chosenDifficulty);

            //This checks if the user's desired number of questions is available for the chosen difficulty
            if (chosenNumQuestions > numQuestions) {
                errorText.setText("There are not enough questions available for your chosen settings. Please choose a different amount of questions");
            } else {
                //This means that there are a valid number of questions to start a quiz, so the program will switch scenes to the quiz application
                SceneOne.show("Quiz");
                SceneOne.hide("Quiz Creator");
            }
        } else {
            //This implies that no category was selected
            System.out.println("Chillin");
        }
    }

    private String getAPIResponse(String apiUrl) {
        StringBuilder response = null;
        try {
            //Generate the url for the given API call
            URL url = new URL(apiUrl);

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
                return response.toString();
            }

            //Disconnect from the connection
            connection.disconnect();

            //Returns blank, as at this point the API call was unsuccessful
            return "";
        } catch (MalformedURLException e) {
            throw new RuntimeException("The given url is malformed. Please reenter the url");
        } catch (IOException e) {
            throw new RuntimeException("The API Call could not be made");
        }
    }

    public ImmutableTriple<Integer, Integer, String> getQuizData() {
        return new ImmutableTriple<>(chosenNumQuestions, chosenId, difficultyHashMap.get(chosenDifficulty).toLowerCase());
    }
}
