/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src;

import com.simtechdata.sceneonefx.SceneOne;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This is the driver class for the program.
 * The program will open with a menu where the user can select numerous options:
 *      "Take a Quiz," where the user can begin a quiz,
 *      "Show Leaderboard," where the user can view a current leaderboard (statistics will be stored in a text file), and
 *      "Exit Program," where the program will exit.
 * After choosing to start a quiz, the user will be able to customize their experience.
 *      The user can choose a quiz topic,
 *      what kind of questions they want asked (Multiple Choice or True/False),
 *      what difficulty of questions they want (Easy, Medium, or Hard),
 *      and various alternative options (Ex: a timer or a set number of lives).
 * At the end of the quiz, the user will be notified of the number of questions they got correct and their total score.
 * The use can then put their name on the leaderboard if they wish, or they can skip it if desired.
 * From this point, the user will have an option to take another quiz or exit the program.
 */
public class QuizApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Loading in the 4 Different Scenes
        Parent quiz = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("QuizQuestion-Layout.fxml")));
        Parent endScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EndScreen-Layout.fxml")));
        //Utilizing the SceneOneFX Library
        SceneOne.set("Quiz", quiz).size(815.0, 600.0).build();
        SceneOne.set("End Screen", endScreen).size(815.0, 600.0).build();
        SceneOne.setTitle("Quiz", "Quiz Application");
        SceneOne.setTitle("End Screen", "Leaderboard");
        SceneOne.show("Quiz");
    }
}
