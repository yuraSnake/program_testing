package com.yura.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HardTest {

    @JsonProperty("number")
    private int number;
    @JsonProperty("listInformation")
    private List<Information> listInformation;

    public HardTest() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Information> getListInformation() {
        return listInformation;
    }

    public void setListInformation(List<Information> listInformation) {
        this.listInformation = listInformation;
    }
}
