package com.multilearning.mlearning.model;

import java.util.ArrayList;

/**
 * Created by mrken on 11/19/15.
 */
public class LessonItem {

    private String lessonId;
    private String titleLesson;

    public LessonItem(){}

    public LessonItem(String lessonId, String titleLesson){
        this.lessonId = lessonId;
        this.titleLesson = titleLesson;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }


    public void setTitleLesson(String titleLesson){
        this.titleLesson = titleLesson;
    }

    public  String getTitleLesson(){
        return titleLesson;
    }

    public String search(ArrayList<LessonItem> list, String key){

        for(int i=0; i<list.size(); i++)
            if(list.get(i).getLessonId().equals(key))
                return list.get(i).getTitleLesson();

        return "not found";
    }

}
