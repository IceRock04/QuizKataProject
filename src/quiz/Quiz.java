package src.quiz;

import java.util.List;

/**
 * The Quiz class will hold all the questions in the quiz, as well as the current score of the player.
 */
public class Quiz {

    private int score;
    private int currentQuestionID;
    private final List<Question> questionList;

    /**
     * This is the Constructor for the Quiz
     * @param questionList is a list of questions
     */
    public Quiz(List<Question> questionList) {
        this.questionList = questionList;
        //The score will always start at 0
        score = 0;
        //The Quiz will start on Question 1 (Starts at 0 in perspective of the questionList object)
        currentQuestionID = -1;
        //Should modify the question's and answer's text for all the question to eliminate special symbols
        for (Question q: questionList) {
            q.parseThroughText();
        }
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
