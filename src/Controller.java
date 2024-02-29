package src;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import src.quiz.Quiz;

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
    }
}
