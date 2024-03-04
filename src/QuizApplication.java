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
 * The user is first brought to a Quiz Creator GUI, where they can create a quiz
 *      They are able to select a category, difficulty, and number of questions for the quiz
 * After creating a quiz, the user is brought to the Quiz GUI to play their quiz
 * At the end of the quiz, the user will be brought to an End Screen GUI, where they will be notified of the number of questions they got correct and their total score.
 * From this point, the user will have an option to take another quiz, where they will return the Quiz Creator, or they can exit the program.
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
        //Creates a new stage for each scene
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
        SceneOne.setTitle("Quiz", "Quiz");
        SceneOne.setTitle("End Screen", "End Screen");

        //Starts the program by showing the Quiz Creator scene
        SceneOne.show("Quiz Creator");

        //Setting up a WindowEvent that calls an "initialize" method in QuizController when the Quiz Scene is shown
        SceneOne.setOnShownEvent("Quiz", windowEvent -> {
            QuizController quizController = quizQuestionLoader.getController();
            QuizCreatorController quizCreatorController = quizCreatorLoader.getController();
            //Calls the pseudo initialize method, which activates once the End Screen scene is shown
            quizController.init(quizCreatorController.getQuizData());
        });
        //Setting up a WindowEvent that calls an "initialize" method in EndScreenController when the End Screen Scene is shown
        SceneOne.setOnShownEvent("End Screen", windowEvent -> {
            EndScreenController controller = endScreenLoader.getController();
            //Calls the pseudo initialize method, which activates once the End Screen scene is shown
            controller.init();
        });
        //Setting up a WindowEvent that calls an "initialize" method in QuizCreatorController when the Quiz Creator Scene is shown
        //The main reason for this one is to allow the player to look back from the End Screen to the Quiz Creator, creating a full loop in the program flow
        SceneOne.setOnShownEvent("Quiz Creator", windowEvent -> {
            QuizCreatorController quizCreatorController = quizCreatorLoader.getController();
            //Calls the pseudo initialize method, which activates once the End Screen scene is shown
            quizCreatorController.init();
        });

    }
}
