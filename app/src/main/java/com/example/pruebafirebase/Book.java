package com.example.pruebafirebase;

public class Book {
    public String title;
    public String author;
    public String year;
    public String imgId;
    public String subject;
    public String language;
    public String amazonId;
    public int state;

    public Book(String title, String author, String year, String imgId, String subject, String language, String amazonId, int state){
        this.title = title;
        this.author = author;
        this.year = year;
        this.imgId = imgId;
        this.subject = subject;
        this.language = language;
        this.amazonId = amazonId;
        this.state = state;
    }

    public Book() {}

    public void finishReading(){
        this.state = 0;
    }

    public void startReading(){
        this.state = 2;
    }

}
