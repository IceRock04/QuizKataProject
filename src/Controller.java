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
     * @param actionEvent
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

        //Trying to load data from API call to QuestionAPILoader class

        URL url = null;
        try {
            url = new URL("https://opentdb.com/api.php?amount=10");

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

                Gson newGson = new Gson();

                QuestionAPILoader loader = gson.fromJson(String.valueOf(response), QuestionAPILoader.class);

                //Fixing this line
                //System.out.println(loader);
            }

            //Disconnect from the connection
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
