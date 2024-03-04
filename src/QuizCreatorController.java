/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src;

import com.google.gson.Gson;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import src.availableQuestions.AvailableQuestionsAPILoader;
import src.category.CategoriesAPILoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the controller for the Quiz Creator scene
 * The user is able to select a category and difficulty from a choice box, and a number of questions from a text input
 * An error message will pop up if any of the following occur
 *      The selected number of questions is unavailable (Ex: User selects 50 questions for a Category/Difficulty selection that only has 20 questions available)
 *      A category was not selected
 *      If the user fails to reenter any information after taking a quiz (The QuizCreatorController fails to retain the previous information from the last quiz, so the user needs to reenter it)
 * There are two private methods, validNumQuestions() and checkingQuizGeneration() that verify if the user inputs are valid for creating a quiz
 */

public class QuizCreatorController {

    @FXML
    private TextField numQuestionsText;
    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private ChoiceBox difficultyChoiceBox;
    @FXML
    private Label errorText;
    private HashMap<Integer, String> categoryHashMap;
    private HashMap<Integer, String> difficultyHashMap;
    private APIUtil apiUtil;
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
        //Checks if the user can create a quiz
        if (chosenId == 0) {
            //This means that the user didn't select a category
            errorText.setText("Please select a category.");
        } else if (!validNumQuestions() || chosenNumQuestions <= 0){
            //This means that the user did not put in a desired number of questions for the quiz, or that the user put in a number lower than 1
            errorText.setText("Please input a desired number of questions for the quiz. Ensure that your number is above 0");
        } else {
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
        //Runs the init() method to set quiz creation values
        init();

        //Get an instance of APIUtil
        apiUtil = APIUtil.APIUtil();

        //Parse the API Call to gather all the categories available for a quiz
        String response = apiUtil.parseAPICall("https://opentdb.com/api_category.php");

        //Create a Gson object
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
        difficultyChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> chosenDifficulty = newValue.intValue());
    }

    /**
     * This is the "initialize" method for the Quiz Creator
     * It resets the quiz creator selections from the previous quiz (or instantiates them when the Quiz Creator is initially shown)
     */
    public void init() {
        //Setting default values for quiz selection
        //The selected number of questions start at 0
        chosenNumQuestions = 0;

        //The chosen category ID and the chosen difficulty are set to "blank" values, which implies that they don't have a preference on category or difficulty
        chosenId = 0;
        chosenDifficulty = -1;

        //Resetting error text
        errorText.setText("");
    }

    private boolean validNumQuestions() {
        //Checks to see if the user input for number of questions is a legit number
        try {
            chosenNumQuestions = Integer.parseInt(numQuestionsText.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void checkingQuizCreation() {
        //Creating a string that will hold the response from the API call
        String response;

        //Creating a Gson object
        Gson gson = new Gson();

        //Creating an int that will hold the number of questions that the user desires based on the chosen difficulty
        int numQuestions;

        //Grabs the response text from the API call
        response = apiUtil.parseAPICall("https://opentdb.com/api_count.php?category=" + chosenId);

        //Creates a QuestionAPILoader object, whose job is to read in the text from the API call
        AvailableQuestionsAPILoader loader = gson.fromJson(response, AvailableQuestionsAPILoader.class);

        //Gets the number of available questions for the current category selection
        numQuestions = loader.getQuestionAmount(chosenDifficulty);

        //This checks if the user's desired number of questions is available for the chosen difficulty
        if (chosenNumQuestions > numQuestions) {
            errorText.setText("There are not enough questions available for your chosen settings. Please choose a different amount of questions. The max number of questions for your current selection is " + numQuestions + ".");
        } else {
            //This means that there are a valid number of questions to start a quiz, so the program will switch scenes to the quiz application
            SceneOne.show("Quiz");
            SceneOne.hide("Quiz Creator");
        }
    }

    /**
     * This method creates an ImmutableTriple, which is used to transfer data from the Quiz Creator to the Quiz for generation of a quiz
     * @return an ImmutableTriple with the chosen number of questions, category, and difficulty (if one is selected)
     */
    public ImmutableTriple<Integer, Integer, String> getQuizData() {
        //Checks to see if a difficulty was selected
        if (difficultyHashMap.get(chosenDifficulty) != null) {
            return new ImmutableTriple<>(chosenNumQuestions, chosenId, difficultyHashMap.get(chosenDifficulty).toLowerCase());
        } else {
            return new ImmutableTriple<>(chosenNumQuestions, chosenId, "");
        }
    }
}
