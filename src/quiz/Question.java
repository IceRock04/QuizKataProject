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
    private final String correct_answer;
    private final List<String> incorrect_answers;

    /**
     *
     * @param type
     * @param difficulty
     * @param category
     * @param question
     * @param correct_answer
     * @param incorrect_answers
     */
    public Question(String type, String difficulty, String category, String question, String correct_answer, List<String> incorrect_answers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
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

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrect_answers;
    }

    /**
     * Checks if the answer selected is the correct answer
     * @param answer is the answer that was selected
     * @return a boolean representing if the selected answer was correct
     */
    public boolean checkCorrectAnswer(String answer) {
        return correct_answer.equals(answer);
    }

    //The purpose of this method is to ensure that special symbols like quotes get correctly implemented.
    private String parseThroughText(String text) {
        //text.replaceAll("")
        return "";
    }

    @Override
    public String toString() {
        return "Question(" +
                "type=" + type.toString()  +
                "," + "difficulty=" + difficulty  +
                "," + "category=" + category  +
                "," + "question=" + question  +
                "," + "correctAnswer=" + correct_answer  +
                "," + "incorrectAnswers" + incorrect_answers.toString() +
                ")";
    }
}
