package com.yura.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("theme")
    private String theme;
    @JsonProperty("answers")
    private List<Answer> answers = new ArrayList<>();

    public Question() {

    }

    public Question(String name, String theme, List<Answer> answers) {
        this.name = name;
        this.theme = theme;
        this.answers = answers;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

/*    public static  getRightAnswer(){
        int right = null;
        for(Answer answer : this.getAnswers()) {
            if(answer.getRight() == true)
                rightAnswer = answer;
        }
        return rightAnswer;
    }*/

}