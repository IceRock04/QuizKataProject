package src;

import com.google.gson.Gson;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import src.quiz.Question;
import src.quiz.QuestionAPILoader;
import src.quiz.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This is the Controller for the Quiz Scene
 * There are numerous Buttons and Labels that help describe the Quiz functionality
 */
public class QuizController {

    private Quiz quiz;
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
    @FXML
    private Label numQuestionText;
    private List<Button> allButtons;
    private boolean hasClickedAnswer = false;

    /**
     * When an answer button is clicked on, the handleCheckAnswer() method will grab the answer text provided.
     * The grabbed text will be sent to the Quiz class to check if it is the correct answer.
     * @param actionEvent represents a click action that occurs when an answer button is clicked
     */
    public void handleCheckAnswer(ActionEvent actionEvent) {
        //Grabs the reference of the button that was just clicked
        Button button = (Button) actionEvent.getTarget();
        //Checks to see if an answer has been selected
        if (hasClickedAnswer) {
            //Makes a check to see if there are any available questions remaining.
            if (quiz.getCurrentQuestionID() >= quiz.getQuestionList().size()) {
                //This means that the quiz has finished and the end screen should display
                SceneOne.show("End Screen");
                return;
            }
            //An answer has already been selected. The next button click will proceed with the next available question
            changeQuestion();
            //An answer has not been clicked for the next question, so this value is set to false
            hasClickedAnswer = false;
        }
        else {
            //Grabs the text from the selected answer and checks to see if it is the correct answer
            if (quiz.checkAnswer(button.getText())) {
                //The correct answer was selected
                button.setText("That is the correct answer!");
                //Highlights the selected button green
                button.setStyle("-fx-background-color: #3dff00; ");
                //Adds the score of the question to the running total
                quiz.addQuestionScore();
                //Updates the score text
                scoreText.setText("Score: " + quiz.getScore());
            } else {
                //The wrong answer was selected
                button.setText("That is the wrong answer.");
                //Highlights the selected button red
                button.setStyle("-fx-background-color: #ff0000;");
                //Finds the correct answer and highlights it green
                for (Button b: allButtons) {
                    if (quiz.checkAnswer(b.getText())) {
                        b.setStyle("-fx-background-color: #3dff00");
                        break;
                    }
                }


            }
            //Sets the question text to inform the user that they can move to the next question
            questionText.setText("Click any button to continue to the next question.");
            //State change to notify that the next button click will change to the next question
            hasClickedAnswer = true;
        }
    }

    private void changeQuestion() {
        //Grabs the information for the next question
        Question currentQuestion = quiz.nextQuestion();

        //Sets the text for what question number it is in the quiz
        numQuestionText.setText("Question Number: " + quiz.getCurrentQuestionID());

        //Sets the text for the question
        questionText.setText(Objects.requireNonNull(currentQuestion).getQuestion());

        //scoreText.setText();
        //Setting up a list that will be used to randomly generate the 2 or 4 answers for the questions
        List<String> allAnswers = new ArrayList<>();

        //Adds the correct answer and other incorrect answers to the answer list
        allAnswers.add(currentQuestion.getCorrectAnswer());
        allAnswers.addAll(currentQuestion.getIncorrectAnswers());

        //Shuffles the answers in the list
        Collections.shuffle(allAnswers);

        //Checks to see if the question is multiple choice or true/false
        if (currentQuestion.getType().equals("multiple")) {
            //There are 4 answers, so all 4 buttons get used
            for (Button button: allButtons) {
                //Sets the answer text for each button
                button.setText(allAnswers.getFirst());
                //Changes the color of the button to the default grey color
                button.setStyle("-fx-background-color: #7e7e7e;");
                //Turns on the 3rd and 4th button, assuming that they were turned off from a true/false question
                button.setVisible(true);
                //Removes an available answer from the list of answers
                allAnswers.removeFirst();
            }
        } else {
            //This is a true/false question, so there are only two answers
            for (int i = 0; i < 2; i++) {
                //Sets the text for the first two buttons
                allButtons.get(i).setText(allAnswers.getFirst());
                //Changes the color of the first two buttons to the default grey color
                allButtons.get(i).setStyle("-fx-background-color: #7e7e7e;");
                //Turns off Buttons 3 and 4 for answers
                allButtons.get(i + 2).setVisible(false);
                //Removes an available answer from the list of answers
                allAnswers.removeFirst();
            }
        }
    }
    public void initialize() {
        allButtons = new ArrayList<>();
        //Adds all 4 buttons to a Button List. Used in later spots
        allButtons.add(answer1);
        allButtons.add(answer2);
        allButtons.add(answer3);
        allButtons.add(answer4);
        //Sets the score text to start at 0 points
        scoreText.setText("Score: 0");
        //Creates a quiz for the selected input responses:
        //Number of Questions, Category Type, Difficulty, Question Type
        createQuiz(10, 15, "medium", "multiple");
        //Begins the Quiz on the first question
        changeQuestion();
    }

    //This method creates a quiz based off of input responses
    private void createQuiz(int numQuestions, int categoryID, String difficulty, String type) {
        //Due to the nature of the API call, if a user wants to select "Any for any of the 3 choices above (Not including the number of questions), the section is left blank.
        //This means that for the other calls besides numQuestions, I need to make checks to see if I add those argument responses.

        //Setting up the url for the API Call
        String urlLink = "https://opentdb.com/api.php?amount=" + numQuestions;

        //Checks to see if a category was selected
        if (categoryID != 0) {
            urlLink += "&category=" + categoryID;
        }

        //Checks to see if a Difficulty was selected
        if (!difficulty.isEmpty()) {
            urlLink += "&difficulty=" + difficulty;
        }

        //Checks to see if a question type was selected
        if (!type.isEmpty()) {
            urlLink += "&type=" + type;
        }

        try {
            //Creates a URL based off the previously built API Call url (Above)
            URL url = new URL(urlLink);

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
                QuestionAPILoader loader = gson.fromJson(String.valueOf(response), QuestionAPILoader.class);

                //Creates a Quiz with the questions obtained from the QuestionAPILoader object
                quiz = new Quiz(loader.getQuestions());
            }
            //Disconnect from the connection
            connection.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
