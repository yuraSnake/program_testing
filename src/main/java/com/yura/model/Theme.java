package com.yura.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class Theme implements Serializable {

    @JsonProperty("name")
    private String name;

    public Theme() {

    }

    public Theme(String name) {
         this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}