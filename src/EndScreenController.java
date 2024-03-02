package src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class EndScreenController {

    @FXML
    private Label totalScoreText;
    @FXML
    private Label leaderboardStatsText;
    @FXML
    private Button playButton;
    @FXML
    private Button exitButton;

    /**
     * This method allows the player to return to the Quiz Creator scene and make another quiz
     * @param actionEvent represents the user clicking on the play button
     */
    public void handlePlayCommand(ActionEvent actionEvent) {
        //SceneOne.show("Quiz Creator");
    }

    /**
     * This method allows the user to safely exit the program
     * @param actionEvent represents the user clicking on the exit button
     */
    public void handleExitCommand(ActionEvent actionEvent) {
        System.out.println("Thank you for playing my quiz!");
        System.exit(1);
    }
}
