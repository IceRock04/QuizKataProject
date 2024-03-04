/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.quiz;

import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

/**
 * This is the Question class, where all the required data for a question is stored.
 * There are multiple variables, such as question type, difficulty, category, question text, and answers.
 * It contains methods to get the score of the question (depends on difficulty) and checking the correct answer for the question
 * There is a private method to fix any HTML Codes found in the question text (From the API Call)
 */
public class Question {
    private final String type;
    private final String difficulty;
    private final String category;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    /**
     * This is the constructor for the Question Object
     * @param type is the type of question, either multiple choice or true/false.
     * @param difficulty is the difficulty of the question, which correlates to the total score of the question.
     * @param category is the category of the question
     * @param question is the question text
     * @param correct_answer is the correct answer for the question
     * @param incorrect_answers are the incorrect answers for the question
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

    /**
     * The purpose of this method is to look through the question and answer text to ensure that special symbols are implemented.
     * Due to the nature of how JSON assigns values to the Question objects, my original attempt at calling this method in the constructor was a vain attempt
     * This method will be called on each question in the Quiz class when a quiz is initialized
     */
    public void parseThroughText() {
        question = checkRegex(question);
        correct_answer = checkRegex(correct_answer);
        incorrect_answers.replaceAll(this::checkRegex);
    }

    /**
     * This is a helper method that fixes any html codes with their appropriate symbols
     * @param text is the line of text that is to be fixed
     * @return a corrected line of text
     */
    public String checkRegex(String text) {
        return StringEscapeUtils.unescapeHtml4(text);
    }

    @Override
    public String toString() {
        return "Question(" +
                "type=" + type +
                "," + "difficulty=" + difficulty  +
                "," + "category=" + category  +
                "," + "question=" + question  +
                "," + "correctAnswer=" + correct_answer  +
                "," + "incorrectAnswers" + incorrect_answers.toString() +
                ")";
    }
}
