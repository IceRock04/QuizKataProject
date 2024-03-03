package src.availableQuestions;

public class AvailableQuestions {
    private int total_question_count;

    private int total_easy_question_count;

    private int total_medium_question_count;

    private int total_hard_question_count;

    /**
     * This method checks the chosen question difficulty and returns the total number of available questions
     * @param type represents the integer version of each difficulty option
     * @return an integer representing the number of available questions
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
