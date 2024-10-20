package com.example.personapi.model;

public class Movie {
    private String title;
    private String genre;
    private int year; // Nuevo campo

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() { // Getter para el año
        return year;
    }

    public void setYear(int year) { // Setter para el año
        this.year = year;
    }
}
