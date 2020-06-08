package com.example.myassignment.Model;

public class Category {
    private String name;
    private String description;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Category(String name, String description, String position) {
        this.name = name;
        this.description = description;
        this.position = position;
    }
}
