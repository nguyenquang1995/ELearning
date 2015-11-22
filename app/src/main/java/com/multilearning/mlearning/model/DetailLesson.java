package com.multilearning.mlearning.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mrken on 11/21/15.
 */
public class DetailLesson implements Serializable{

    private String question;
    private String answer;
    private ArrayList<String> listChosse;

    public DetailLesson(){}

    public DetailLesson(String question, ArrayList<String> listChosse, String answer){
        this.question = question;
        this.listChosse = new ArrayList<>(listChosse);
        this.answer = answer;
    }

    public ArrayList<String> getListChosse() {
        return listChosse;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
