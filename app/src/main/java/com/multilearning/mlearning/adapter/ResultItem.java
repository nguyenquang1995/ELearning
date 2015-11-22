package com.multilearning.mlearning.adapter;

import java.io.Serializable;

/**
 * Created by mrken on 11/22/15.
 */
public class ResultItem implements Serializable{

    private String question;
    private String answer;
    private Boolean status;

    public ResultItem(){}

    public ResultItem(String question, String answer, Boolean status){
        this.question = question;
        this.answer = answer;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
