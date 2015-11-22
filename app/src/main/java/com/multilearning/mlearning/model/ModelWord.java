package com.multilearning.mlearning.model;

/**
 * Created by hacks_000 on 11/15/2015.
 */
public class ModelWord {
    private int id;
    private String content;
    private int category_id;

    public ModelWord(int id, String content, int category_id) {
        this.id = id;
        this.content = content;
        this.category_id = category_id;
    }

    public String getContent() {
        return content;
    }
}
