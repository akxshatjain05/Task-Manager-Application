package com.example.task_manager.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.task_manager.data.entity.TaskEntity;
import com.example.task_manager.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private TaskRepository repository;
    private LiveData<List<TaskEntity>> allTasks;

    public TaskViewModel(TaskRepository repository) {
        this.repository = repository;
        this.allTasks = repository.getAllTasks();  // Fetch LiveData from repository
    }

    public LiveData<List<TaskEntity>> getTasks() { return allTasks; }

    public void addTask(TaskEntity task) { repository.insertTask(task); }
    public void updateTask(TaskEntity task) { repository.updateTask(task); }
    public void deleteTask(TaskEntity task) { repository.deleteTask(task); }
}
