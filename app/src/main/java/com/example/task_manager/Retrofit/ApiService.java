package com.example.task_manager.Retrofit;

import com.example.task_manager.model.Task;
import com.example.task_manager.data.entity.TaskEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("todos")
    Call<List<TaskEntity>> getTasks();
}
