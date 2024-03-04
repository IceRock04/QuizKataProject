/*
 * February 2024
 * Quiz Kata Project
 * Name: Jacob Minikel
 * Created 2/28/2024
 */
package src.quiz;

import java.util.List;

/**
 * The Quiz class will hold all the questions in the quiz, as well as the current score of the player.
 * There is a Current Question ID number to reference what question the user is on
 * A list of questions is contained within this class
 * The Quiz class was converted to a singleton to allow me to call the quiz from numerous controllers without passing the quiz object around
 *      This is because I will be using the same quiz object in both the Quiz Creator and Quiz Controllers for different reasons, so having an easy reference to the Quiz object is helpful
 * There is also a variable to track the number of questions the user got correct in the quiz. Depending on the number of questions correct, a different victory message displays at the End Screen
 */
public class Quiz {

    private static Quiz quizInstance = null;
    private int score;
    private int currentQuestionID;
    private int numCorrect;
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

        //Sets the number of questions correct to 0
        numCorrect = 0;
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

    public int getCurrentQuestionID() {
        return currentQuestionID + 1;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    /**
     * This method is to inform the user of how many questions they got correct and give a victory message
     * @return a string representing a victory message for the user
     */
    public String victoryMessage() {
        //Checks how many questions the user got correct
        if (numCorrect == questionList.size()) {
            //The user got all the questions correct!
            return "You got all the questions correct! Congratulations!";
        } else if (numCorrect > questionList.size() / 2) {
            //The user got at least half of the questions correct
            return "You got at least half of the questions correct. Good Job! Total number of questions correct: " + numCorrect;
        } else {
            //The user got less than half of the questions correct
            return "You got less than half of the questions correct. Better luck next time. Total number of questions correct: " + numCorrect;
        }
    }

    /**
     * This method is used to change the list of questions for the Quiz
     * When the questions change, the score and current question reset to their default values
     * @param questions is a new list of questions
     */
    public void changeQuestions(List<Question> questions) {
        //Resetting quiz values
        score = 0;
        currentQuestionID = -1;
        questionList = questions;
        numCorrect = 0;
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
     * @param originalClick represents if the checkAnswer call was a real answer click
     *      (This method is used elsewhere, so turning originalClick to false avoids updating numCorrect counter
     * @return a boolean representing if the selected answer is correct
     */
    public boolean checkAnswer(String answer, boolean originalClick) {
        if (originalClick) {
            //Checks to see if the selected answer was correct (Original Answer Click for points)
            if (questionList.get(currentQuestionID).checkCorrectAnswer(answer)) {
                //Increments the number of questions answers correct by 1
                numCorrect++;
                return true;
            }
            return false;
        } else {
            //Checks to find if the answer text is correct (For determining answer color in QuizController. Not for points)
            return questionList.get(currentQuestionID).checkCorrectAnswer(answer);
        }

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
