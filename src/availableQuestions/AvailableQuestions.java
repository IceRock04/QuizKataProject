/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.availableQuestions;

/**
 * This class is used as a JSON object container
 * It stores the number of questions for the total amount of available questions
 * It also stores the number of questions for each difficulty
 */
public class AvailableQuestions {
    private int total_question_count;

    private int total_easy_question_count;

    private int total_medium_question_count;

    private int total_hard_question_count;

    /**
     * This method checks the chosen question difficulty and returns the total number of available questions
     * @param type represents the integer version of each difficulty option
     * @return an integer representing the number of available questions
     * @throws IllegalStateException if an unexpected difficulty options is given
     */
    public int getQuestionType(int type) {
        return switch (type) {
            case -1 -> total_question_count;
            case 0 -> total_easy_question_count;
            case 1 -> total_medium_question_count;
            case 2 -> total_hard_question_count;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    @Override
    public String toString() {
        return "Total Questions: " + total_question_count + "\n" +
                "Total Easy Questions: " + total_easy_question_count + "\n" +
                "Total Medium Questions: " + total_medium_question_count + "\n" +
                "Total Hard Questions: " + total_hard_question_count + "\n";
    }
}
