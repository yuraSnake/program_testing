package com.yura.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope("singleton")
public class ArrayBlank {

    private List<Blank> blankList = new ArrayList<>();

    public ArrayBlank() {

    }

    public List<Blank> getBlankList() {
        return this.blankList;
    }

}
