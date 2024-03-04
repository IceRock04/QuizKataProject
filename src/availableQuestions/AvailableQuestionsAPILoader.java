/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.availableQuestions;

/**
 * This class was created to receive API data for the number of questions available for a specific category
 * It stores the id for the given category, as well as an object that holds the number of available questions for each difficulty
 */
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
