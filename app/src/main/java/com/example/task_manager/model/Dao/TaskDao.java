package com.example.task_manager.model.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.task_manager.model.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    LiveData<List<TaskEntity>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTasks(List<TaskEntity> tasks);

    @Update
    void updateTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);
}

