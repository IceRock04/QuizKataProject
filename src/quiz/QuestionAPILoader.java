/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.quiz;


import java.util.List;

/**
 * This class is for receiving the data from the API Call made in the Controller class.
 * It holds a response code from the API call, as well as a list of questions (To be used by the Quiz Class)
 * The main reason why this class exists is so that I can handle different response codes from the API call,
 *      assuming that it does not return zero.
 */
public class QuestionAPILoader {
    private int response_code;
    private List<Question> results;

    public List<Question> getQuestions() {
        return switch (response_code) {
            case 0 -> results;
            case 1 -> throw new RuntimeException("Not Enough Questions Available");
            case 2 -> throw new RuntimeException("Invalid Parameter In API Call");
            case 3 -> throw new RuntimeException("Token Not Found");
            case 4 -> throw new RuntimeException("All Questions Exhausted From Current Selection");
            case 5 -> throw new RuntimeException("Too Many API Calls At Once");
            default -> null;
        };
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(response_code);
        result.append("\n");
        for (Question q: results) {
            result.append(q.toString());
            result.append("\n");
        }
        return result.toString();
    }
}
