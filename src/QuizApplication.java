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
import javafx.stage.WindowEvent;
import src.quiz.Quiz;

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
 *
 * The design decision for making 3 different FXML Loaders separately is so that I can make WindowEvents for the Scenes
 * If I were to stick with the traditional Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
 *      Then I would not be able to create window events AFTER the creation and loading of the Scene
 *      My initial attempt was to create window events in the initialize() methods of each controller class,
 *      but it ended up not working out well.
 */
public class QuizApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneOne.newStageEveryScene();
        //Creating FXML Loaders for the 3 different Scenes
        FXMLLoader quizCreatorLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("QuizCreation-Layout.fxml")));
        FXMLLoader quizQuestionLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("QuizQuestion-Layout.fxml")));
        FXMLLoader endScreenLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("EndScreen-Layout.fxml")));
        //Creating Parent objects based on the FXML layouts
        Parent quizCreator = quizCreatorLoader.load();
        Parent quiz = quizQuestionLoader.load();
        Parent endScreen = endScreenLoader.load();
        //Utilizing the SceneOneFX Library to set up the scenes for the program
        SceneOne.set("Quiz Creator", quizCreator).size(815.0, 600.0).build();
        SceneOne.set("Quiz", quiz).size(815.0, 600.0).build();
        SceneOne.set("End Screen", endScreen).size(815.0, 600.0).build();
        //Setting titles for each of the scenes
        SceneOne.setTitle("Quiz Creator", "Quiz Creator");
        SceneOne.setTitle("Quiz", "Quiz Application");
        SceneOne.setTitle("End Screen", "Leaderboard");
        //Starts the program by showing the Quiz Creator scene
        SceneOne.show("Quiz Creator");
        //Setting up a WindowEvent that calls an "initialize" method in EndScreenController when the End Screen Scene is shown
        SceneOne.setOnShownEvent("End Screen", windowEvent -> {
            EndScreenController controller = endScreenLoader.getController();
            //Calls the pseudo initialize method, which activates once the End Screen scene is shown
            controller.init();
        });
    }
}
