package src.quiz;

import java.util.List;

/**
 * The Quiz class will hold all the questions in the quiz, as well as the current score of the player.
 * There is a Current Question ID number to reference what question the user is on
 * A list of questions is contained within this class
 * The Quiz class was converted to a singleton to allow me to call the quiz from numerous controllers without passing the quiz object around
 *      This is because I will be using the same quiz object in both the Quiz Creator and Quiz Controllers for different reasons
 *
 */
public class Quiz {

    private static Quiz quizInstance = null;
    private int score;
    private int currentQuestionID;
    private List<Question> questionList;

    /**
     * This is the Constructor for the Quiz for when it is initially created
     */
    private Quiz() {
        //The score will always start at 0
        score = 0;
        //The Quiz will start on Question 1 (Starts at 0 in perspective of the questionList object)
        currentQuestionID = -1;
        //Sets the quiz list to be initially null, as when it is instantiated, no questions will be available
        questionList = null;
    }

    /**
     * A static method that utilizes the Singleton pattern to ensure that only one quiz is ever made
     * @return the quiz instance
     */
    public static Quiz Quiz() {
        if (quizInstance == null) {
            quizInstance = new Quiz();
        }
        return quizInstance;
    }

    public int getScore() {
        return score;
    }

    /**
     * This method gets the current question number
     * @return an integer representing the current question number for the current quiz
     */
    public int getCurrentQuestionID() {
        return currentQuestionID + 1;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    /**
     * This method is used to change the list of questions for the Quiz
     * When the questions change, the score and current question reset to their default values
     * @param questions is a new list of questions
     */
    public void changeQuestions(List<Question> questions) {
        score = 0;
        currentQuestionID = -1;
        questionList = questions;
        //Fixes any HTML code text in the questions
        for (Question q: questionList) {
            q.parseThroughText();
        }
    }

    /**
     * This method adds the score of the question to the total quiz score
     */
    public void addQuestionScore() {
        score += questionList.get(currentQuestionID).getScore();
    }
    /**
     * This method checks the current question on the quiz and sees if the selected answer is correct
     * @param answer the text of the answer that was selected
     * @return a boolean representing if the selected answer is correct
     */
    public boolean checkAnswer(String answer) {
        return questionList.get(currentQuestionID).checkCorrectAnswer(answer);
    }

    /**
     * This method checks to see if there are any more questions available in the quiz, and if so, returns the next question.
     * @return a Question representing the next question of the quiz
     */
    public Question nextQuestion() {
        Question question = null;
        //Advances to the next question
        currentQuestionID++;
        //Checks to see if there are any remaining questions left in the quiz
        if(currentQuestionID < questionList.size()) {
            question = questionList.get(currentQuestionID);
        }
        return question;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Current Score: " + score);
        //Iterates through the list of Questions
        for (Question q: questionList) {
            result.append("\n").append(q.toString());
        }
        return result.toString();
    }
}
