# QuizKataProject
This repository is for the implementation and creation of a Quiz Application. It will utilize the Open Trivia Database as the source of the program's quiz questions.
https://opentdb.com/api_config.php

*This repository was made for a Direct Supply project*

Libraries Used:

SceneOneFX - Version 1.3.9

Apache Commons Lang 3 - Version 3.13.0

Apache Commons Text - Version 1.11.0

Gson - Version 2.10.1

Program Sequence:
The user first starts at a Quiz Creator, where they can select the category, difficulty, and number of questions they want for their quiz.
After creating a quiz, the user plays their quiz and tries to answer as many questions as possible. The questions may either be multiple choice or true/false.
After completing the quiz, the user is informed of their total score and the number of questions they got correct. 
From here, the user may either create another quiz or exit the program.

*This is a basic overview of the different classes and their basic functions. More details are within Javadocs comments of each respective file*

File Aspects:
The application consists of a QuizApplication file, which loads in the different FXML files and creates SceneOneFX scenes for each one. 
There are 3 different controllers throughout the program:
1. A QuizCreatorController that keeps track of the different inputs/selections the user gives. It then verifies that a quiz can be created with the given data and gives the data to the Quiz Controller.
2. The QuizController handles the behind-the-scenes for the actual quiz. It displays the current question and the possible answers. It also gives feedback on whether the selected answer is correct or not.
3. An EndScreenController that displays the final score and number of questions the user got correct. It allows the user to create another quiz or exit the program safely.
There is a file called APIUtil that does most of the groundwork for handling API Calls, which includes setting up the connection and parsing the API data into a String.
There are 3 packages that all relate to different API data that may be passed:
1. availableQuestions (which tracks how many questions are available for a specific category).
2. category (which determines all the available categories that a quiz can take).
3. quiz (which gathers the questions for the quiz and handles most of the quiz data).

Challenges:
There were numerous challenges that I faced while creating this application, so I will go over a few and what I did to solve them.

1. How to get data from the Open Trivia Database into my application?

  This first step was probably the hardest one for me, as it was my first time delving into API Calls and JSON objects.
  I started by looking at online tutorials and reading Oracle pages to learn more about the methods and structure for managing API calls.
  After managing to make my first API call, I then had to find a way to store that information. That is where I started looking into the Gson library. 
  The simplicity of the Gson library allowed me to make basic Gson objects and data containers for the information from the API call.

2. How to fix broken HTML codes that remained in the API Call responses?

   I noticed this issue after I was finally able to display the information from the API Calls, and the issue was that HTML codes were left in the response data.
   This meant that symbols such as quotation marks were instead parsed as "&quot," a much less desirable format.
   I thought about handwriting text replacers to fix these codes, but that is when I decided to look for a library for assistance.
   I found the Apache Commons Text library, which allowed me to call a method to parse and fix any remaining HTML codes in the API response.

3. How to transfer data from one scene to another?

   This was likely the second hardest challenge to solve, as it was a crucial step to allow my program to function.
   I first thought of creating a Singleton Class to hold all the data I wanted to pass. This would allow me to have a single reference to an object that I can call from any of my controllers.
   The issue was that I didn't want to create multiple Singleton Classes just for this purpose, as this approach felt like a poor imitation of what I wanted to accomplish.
   After doing extensive research, I found out about WindowEvents.
   I learned that I could set up Window Events to trigger when a scene is shown, so I was able to freely pass information through controller-specific methods meant for passing data.
   One way I made the data transferring between scenes work more smoothly was through the usage of the Apache Commons Lang 3 library.
   The Lang 3 library had access to ImmutableTuples, which gave me the option to return multiple pieces of data at once from a method with ease.

The SceneOneFX library was used to allow for easy Scene transitions. It also assisted in creating the Window Events described in challenge 3. 
