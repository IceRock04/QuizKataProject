package src.quiz;


import java.util.List;

/**
 * This class is for receiving the data from the API Call made in the Controller class.
 * The main reason why this class exists is so that I can handle different response codes from the API call,
 *      assuming that it does not return zero.
 */
public class QuestionAPILoader {
    private int response_code;
    private List<Question> results;


    private int getResponseCode() {
        return response_code;
    }

    public List<Question> getQuestions() {
        return switch (getResponseCode()) {
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
