package src.quiz;

import java.util.List;

/**
 * This is the Question class, where all the required data for a question is stored.
 * There are multiple variables, such as question type, difficulty, question text, category, and answers.
 */
public class Question {
    private final String type;
    private final String difficulty;
    private final String category;
    private final String question;
    private final String correctAnswer;
    private final List<String> incorrectAnswers;

    /**
     *
     * @param type
     * @param difficulty
     * @param category
     * @param question
     * @param correctAnswer
     * @param incorrectAnswers
     */
    public Question(String type, String difficulty, String category, String question, String correctAnswer, List<String> incorrectAnswers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    /**
     * This method checks the difficulty of the question and returns its appropriate score.
     * @return an int that represents the score amount relative to its difficulty.
     */
    public int getScore() {
        if (difficulty.equals("easy")) {
            return 10;
        } else if(difficulty.equals("medium")) {
            return 20;
        }
        return 40;
    }

    /**
     * Checks if the answer selected is the correct answer
     * @param answer is the answer that was selected
     * @return a boolean representing if the selected answer was correct
     */
    public boolean checkCorrectAnswer(String answer) {
        return correctAnswer.equals(answer);
    }

    @Override
    public String toString() {
        return "Question(" +
                "type=" + type.toString()  +
                "," + "difficulty=" + difficulty  +
                "," + "category=" + category  +
                "," + "question=" + question  +
                "," + "correctAnswer=" + correctAnswer  +
                "," + "incorrectAnswers" + incorrectAnswers.toString() +
                ")";
    }
}
