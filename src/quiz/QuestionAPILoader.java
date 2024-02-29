package src.quiz;


import java.util.List;

public class QuestionAPILoader {
    private int response_code;
    private List<Question> results;

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
