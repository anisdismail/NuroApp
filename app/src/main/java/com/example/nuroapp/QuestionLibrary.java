package com.example.nuroapp;

public class QuestionLibrary {

    private String myQuestions [] ={
            "Which of the following is not part of the main ingredients of an ML project?",
            "Which of the following is a classification example?",
    };
    private String myChoices [][]={
            {"Machine Learning Model","Dataset","Cybersecurity","Training Algorithm"},
            {"Housing prices prediction","Stock prices prediction","Face detection","Grades prediction"}

    };

    private String myCorrectAnswers []={"Cybersecurity","Face detection"};


    public String getQuestion(int a){
        String question = myQuestions[a];
        return question;
    }
    public String getChoice1(int a){
        String choice0 = myChoices[a][0];
        return choice0;
    }
    public String getChoice2(int a){
        String choice1 = myChoices[a][1];
        return choice1;
    }
    public String getChoice3(int a){
        String choice2= myChoices[a][2];
        return choice2;
    }
    public String getChoice4(int a){
        String choice3 = myChoices[a][3];
        return choice3;
    }
    public String getCorrectAnswer(int a){
        String answer = myCorrectAnswers[a];
        return answer;
    }


}
