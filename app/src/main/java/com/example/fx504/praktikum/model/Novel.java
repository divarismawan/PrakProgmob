package com.example.fx504.praktikum.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Novel {
    private String Title;
    private String Genre;
    private String Description;
    private int Thubnail;

    public Novel(){
    }

    public Novel(String title, String genre, String description, int thubnail) {
        Title = title;
        Genre = genre;
        Description = description;
        Thubnail = thubnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDescription() {
        return Description;
    }

    public int getThubnail() {
        return Thubnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThubnail(int thubnail) {
        Thubnail = thubnail;
    }
}
