package src;

import com.simtechdata.sceneonefx.SceneOne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import src.quiz.Quiz;

/**
 * This is the controller class for the End Screen (Aka, Leaderboard)
 * The user will be asked for their name abbreviations (Max 3 characters long) upon landing on this screen.
 * A label will display the user's abbreviations and the total score they accumulated throguhout the quiz.
 * A leaderboard will be displayed, showing the top 10 users and their highest score.
 * There will be two buttons for the user to click to continue through the program.
 *      The user can click a "Play again?" button to return to the quiz creator and make another quiz.
 *      The user can click an "Exit Program" button to exit the program.
 */
public class EndScreenController {

    @FXML
    private Label totalScoreText;
    @FXML
    private Label leaderboardStatsText;
    @FXML
    private Button playButton;
    @FXML
    private Button exitButton;
    private Quiz quiz = Quiz.Quiz();;

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

    public void init() {
        totalScoreText.setText("You got a total score of " + quiz.getScore() + "!");
    }
}
