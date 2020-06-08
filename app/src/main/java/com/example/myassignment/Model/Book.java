package com.example.myassignment.Model;

public class Book {
    private String bookName;
    private String categoryName;
    private String title;
    private String author;
    private String date;
    private long money;
    private int number;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Book(String bookName, String categoryName, String title, String author, String date, long money, int number) {
        this.bookName = bookName;
        this.categoryName = categoryName;
        this.title = title;
        this.author = author;
        this.date = date;
        this.money = money;
        this.number = number;
    }
}
