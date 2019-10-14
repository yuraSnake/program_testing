package com.yura.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class ArrayTheme {

    private List<Theme> themeList = new ArrayList<>();

    public ArrayTheme() {
        this.getThemeList().add(new Theme("no theme"));
    }

    public List<Theme> getThemeList() {
        return this.themeList;
    }

}