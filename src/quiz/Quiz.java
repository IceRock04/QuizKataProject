package src.quiz;

import java.util.List;

/**
 * The Quiz class will hold all the questions in the quiz, as well as the current score of the player.
 */
public class Quiz {

    private final int score;
    private final List<Question> questionList;

    /**
     * This is the Constructor for the Quiz
     * @param questionList is a list of questions
     */
    public Quiz(List<Question> questionList) {
        this.questionList = questionList;
        //The score will always start at 0
        score = 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Current Score: " + score);
        //Iterates through the list of Questions
        for (Question q: questionList) {
            result.append("\n").append(q.toString());
        }
        return result.toString();
    }
}
