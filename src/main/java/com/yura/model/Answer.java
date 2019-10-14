package com.yura.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Answer implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("right")
    private boolean right;

    public Answer() {

    }

    public Answer(String name) {
        this.name = name;
    }

    public boolean getRight() {
        return this.right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
