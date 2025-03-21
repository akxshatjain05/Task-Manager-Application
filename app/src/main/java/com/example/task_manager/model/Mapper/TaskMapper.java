package com.example.task_manager.model.Mapper;

import com.example.task_manager.model.Task;
import com.example.task_manager.model.TaskEntity;

public class TaskMapper {
    public static TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity(task.getTitle(), task.isCompleted());
        entity.setId(task.getId());
        return entity;
    }

    public static Task toModel(TaskEntity entity) {
        return new Task(entity.getId(), entity.getTitle(), entity.isCompleted());
    }
}
