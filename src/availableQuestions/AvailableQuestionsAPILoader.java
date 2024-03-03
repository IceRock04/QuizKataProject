package src.availableQuestions;

import src.availableQuestions.AvailableQuestions;

public class AvailableQuestionsAPILoader {
    private Integer category_id;

    private AvailableQuestions category_question_count;

    /**
     * This method calls a method from AvailableQuestions to check the number of questions available for a certain difficulty
     * @param type represents the difficulty selected by the user
     * @return an int representing the number of questions availabe
     */
    public int getQuestionAmount(int type) {
        return category_question_count.getQuestionType(type);
    }

    @Override
    public String toString() {
        return "ID: " + category_id + "\n" +
                category_question_count;
    }
}
