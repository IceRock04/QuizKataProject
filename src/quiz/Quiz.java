package src.quiz;

import java.util.List;

/**
 * The Quiz class will hold all the questions in the quiz, as well as the current score of the player.
 * This class will utilize the Singleton Pattern, as only one quiz object should ever be needed.
 *      This decision allows my controller to never have to hold an instance to the Quiz class
 */
public class Quiz {

    private int score;
    private List<Question> questionList;
    public Quiz () {

    }
}
