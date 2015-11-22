package com.multilearning.mlearning.model;

/**
 * Created by mrken on 11/21/15.
 */
public class ResultItem {

    private String status;
    private String question;
    private String answer;

    public ResultItem(String question, String answer, String status){
        this.question = question;
        this.answer = answer;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
