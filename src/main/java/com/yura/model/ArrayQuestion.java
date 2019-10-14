package com.yura.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class ArrayQuestion {

    private ArrayList<Question> questionArrayList = new ArrayList<>();

    public ArrayQuestion() {
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                File file = new File("base/base.json");
                this.questionArrayList = objectMapper.readValue(file, new TypeReference<List<Question>>(){});
                System.out.println("is work");
            } catch(Exception e) {
                System.out.println("is not work");
                e.fillInStackTrace();
            }
        }
    }

    public ArrayList<Question> getQuestionArrayList() {
        return this.questionArrayList;
    }

}