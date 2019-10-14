package com.yura.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Blank implements Serializable {

    private int number;
    private List<Question> questions = new ArrayList<>();

    public Blank() {

    }

    public Blank(int number, List<Question> questions) {
        this.number = number;
        this.questions = questions;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}