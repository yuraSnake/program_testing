package com.yura.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Information {

    @JsonProperty("countQuestion")
    private int countQuestion;
    @JsonProperty("nameTheme")
    private String nameTheme;

    public Information() {}

    public int getCountQuestion() {
        return countQuestion;
    }

    public void setCountQuestion(int countQuestion) {
        this.countQuestion = countQuestion;
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public void setNameTheme(String nameTheme) {
        this.nameTheme = nameTheme;
    }
}
