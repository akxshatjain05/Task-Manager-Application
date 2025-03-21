package com.example.task_manager.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.task_manager.database.AppDatabase;
import com.example.task_manager.model.TaskEntity;
import com.example.task_manager.model.Dao.TaskDao;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        taskDao = db.taskDao();
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public void insertTask(TaskEntity task) {
        new Thread(() -> taskDao.insertTask(task)).start();
    }

    public void updateTask(TaskEntity task) {
        new Thread(() -> taskDao.updateTask(task)).start();
    }

    public void deleteTask(TaskEntity task) {
        new Thread(() -> taskDao.deleteTask(task)).start();
    }
}
