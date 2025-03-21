package com.example.task_manager.model;

public class Task {
    private int id;
    private String title;
    private boolean completed;

    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
