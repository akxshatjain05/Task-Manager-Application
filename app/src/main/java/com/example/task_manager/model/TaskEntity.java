package com.example.task_manager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private boolean completed;

    public TaskEntity(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
