package src;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import src.quiz.Question;
import src.quiz.QuestionAPILoader;
import src.quiz.Quiz;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class Controller {

    @FXML
    private Button answer1;
    @FXML
    private Button answer2;
    @FXML
    private Button answer3;
    @FXML
    private Button answer4;
    @FXML
    private Label timerText;
    @FXML
    private Label scoreText;
    @FXML
    private Label questionText;


    public Controller(){

    }

    /**
     * When an answer button is clicked on, the handleCheckAnswer() method will grab the answer text provided.
     * The grabbed text will be sent to the Quiz class to check if it is the correct answer.
     * @param actionEvent represents a click action that occurs when an answer button is clicked
     */
    public void handleCheckAnswer(ActionEvent actionEvent) {
        //Grabs the reference of the button that was just clicked
        Button button = (Button) actionEvent.getTarget();
        //Grabs the text of the button, which will be passed through the Quiz class to check if it is the right answer
        //System.out.println(button.getText());
        //This next line will return a String. The response will either say that the user got the correct answer, or it will say that the answer is wrong and show the true answer.
        //quiz.checkAnswer(button.getText());

    }

    private void changeQuestion() {
        //Code here to change the text on all the buttons/labels that relate to a question (Label to show the question, and the text on the buttons to show answers)
    }
    //Put some stuff here. Maybe load the Quiz object here and work from there?
    public void initialize() {
        //this.quiz = new Quiz();
        questionText.textProperty().set("Hello");

        Question sampleQuestion = new Question("multiple", "easy", "Art", "What is my favorite art?", "Abstract", new ArrayList<>(Arrays.asList("Poetry", "Games", "Music")));
        //System.out.println(sampleQuestion);

        //Testing GSON Library
        Gson gson = new Gson();

        //Converting to JSON
        String myJson = gson.toJson(sampleQuestion);

        //Testing print capability of JSON format
        System.out.println(myJson);

        //Testing the quiz functionality. Seeing if a quiz can be created
        createQuiz(10, 10, "medium", "multiple");
    }

    //This method creates a quiz based off of input responses
    private void createQuiz(int numQuestions, int categoryID, String difficulty, String type) {
        //Due to the nature of the API call, if a user wants to select "Any for any of the 3 choices above (Not including the number of questions), the section is left blank
        //This means that for the other calls besides numQuestions, I need to make checks to see if I add those argument responses

        //Setting up the url for the API Call
        String urlLink = "https://opentdb.com/api.php?amount=" + numQuestions;

        //Checks to see if a category was selected
        if (categoryID != 0) {
            urlLink += "&category=" + categoryID;
        }
        if (!difficulty.isEmpty()) {
            urlLink += "&difficulty=" + difficulty;
        }
        if (!type.isEmpty()) {
            urlLink += "&type=" + type;
        }

        try {
            URL url = new URL(urlLink);

            //Opens the connection. May throw IOException
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Sets the request method, which in this case is "GET"
            connection.setRequestMethod("GET");

            //Check to see if the connection has a valid response code (200) before proceeding
            if (connection.getResponseCode() == 200) {
                //Reads the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String text;
                while ((text = reader.readLine()) != null) {
                    response.append(text);
                }
                reader.close();

                //Prints out the response
                //System.out.println(response);

                //Creates a Gson object
                Gson gson = new Gson();

                //Creates a QuestionAPILoader object, whose job is to read in the text from the API call
                QuestionAPILoader loader = gson.fromJson(String.valueOf(response), QuestionAPILoader.class);

                //Creates a Quiz with the questions obtained from the QuestionAPILoader object
                Quiz quiz = new Quiz(loader.getQuestions());
            }
            //Disconnect from the connection
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
