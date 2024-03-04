/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src;

import com.simtechdata.sceneonefx.SceneOne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import src.quiz.Quiz;

/**
 * This is the controller class for the End Screen
 * There is a label to inform the user of how many points they scored throughout the quiz
 * A message will pop up to let the user know of how many questions they got correct
 * There will be two buttons for the user to click to continue through the program.
 *      The user can click a "Play again?" button to return to the quiz creator and make another quiz.
 *      The user can click an "Exit Program" button to exit the program.
 */
public class EndScreenController {

    @FXML
    private Label victoryText;
    @FXML
    private Label totalScoreText;
    private final Quiz quiz = Quiz.Quiz();

    /**
     * This method allows the player to return to the Quiz Creator scene and make another quiz
     * @param actionEvent represents the user clicking on the play button
     */
    public void handlePlayCommand(ActionEvent actionEvent) {
        //Brings the user back to the Quiz Creator
        SceneOne.show("Quiz Creator");

        //Hides the End Screen
        SceneOne.hide("End Screen");
    }

    /**
     * This method allows the user to safely exit the program
     * @param actionEvent represents the user clicking on the exit button
     */
    public void handleExitCommand(ActionEvent actionEvent) {
        System.out.println("Thank you for playing my quiz!");
        System.exit(1);
    }

    /**
     * This is an "initialize" method for when the user completes the quiz
     * It sets the text for two labels. One to show the total score of the quiz, and another to display a victory message
     */
    public void init() {
        //Sets the text to show the total score of the quiz
        totalScoreText.setText("You got a total score of " + quiz.getScore() + "!");

        //Sets the text to show the user a victory message
        victoryText.setText(quiz.victoryMessage());
    }
}
