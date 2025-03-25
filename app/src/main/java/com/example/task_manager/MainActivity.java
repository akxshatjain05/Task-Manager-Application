package com.example.task_manager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_manager.Retrofit.ApiService;
import com.example.task_manager.Retrofit.RetrofitClient;
import com.example.task_manager.database.AppDatabase;
import com.example.task_manager.model.TaskEntity;
import com.example.task_manager.ui.adapter.TaskAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.HttpMetric;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics firebaseAnalytics;
    private TaskAdapter taskAdapter;
    private List<TaskEntity> taskList = new ArrayList<>();
    private EditText taskInput;
    private Button addTaskButton, crashButton;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logFirebaseEvent("app_opened", "User opened the app");

        database = AppDatabase.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        taskInput = findViewById(R.id.taskInput);
        addTaskButton = findViewById(R.id.addTaskButton);
        crashButton = findViewById(R.id.crashButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter(taskList, updatedTask -> {
            Log.d("Task Update", "Updated task: " + updatedTask.getTitle());
            saveTaskToDatabase(updatedTask);

            if (updatedTask.isCompleted()) {
                logFirebaseEvent("task_completed", updatedTask.getTitle());
            } else {
                logFirebaseEvent("task_edited", updatedTask.getTitle());
            }
        });

        recyclerView.setAdapter(taskAdapter);

        loadTasksFromDatabase();
        fetchTasksFromApi();

        addTaskButton.setOnClickListener(v -> {
            String taskText = taskInput.getText().toString().trim();

            if (taskText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a valid task name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!taskText.isEmpty()) {
                TaskEntity newTask = new TaskEntity(taskText, false);
                taskList.add(newTask);
                taskAdapter.notifyItemInserted(taskList.size() - 1);
                taskInput.setText("");

                saveTaskToDatabase(newTask);
                Toast.makeText(MainActivity.this, "Task successfully added", Toast.LENGTH_SHORT).show();
                logFirebaseEvent("task_added", newTask.getTitle());
            }
        });

        // Simulate a crash for Firebase Crashlytics
        crashButton.setOnClickListener(v -> {
            throw new RuntimeException("Test Crash for Firebase Crashlytics");
        });
    }

    private void loadTasksFromDatabase() {
        database.taskDao().getAllTasks().observe(this, dbTasks -> {
            taskList.clear();
            taskList.addAll(dbTasks);
            taskAdapter.notifyDataSetChanged();
        });
    }

    private void saveTaskToDatabase(TaskEntity task) {
        new Thread(() -> {
            try {
                database.taskDao().insertTask(task);
            } catch (Exception e) {
                logFirebaseEvent("database_error", "Error inserting task: " + e.getMessage());
                throw new RuntimeException("Database error: " + e.getMessage());
            }
        }).start();
    }

    private void fetchTasksFromApi() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        HttpMetric metric = FirebasePerformance.getInstance().newHttpMetric(
                "https://your-api-url.com/tasks",
                FirebasePerformance.HttpMethod.GET
        );
        metric.start();

        Call<List<TaskEntity>> call = apiService.getTasks();
        call.enqueue(new Callback<List<TaskEntity>>() {
            @Override
            public void onResponse(Call<List<TaskEntity>> call, Response<List<TaskEntity>> response) {
                metric.setHttpResponseCode(response.code());
                metric.stop(); // Stop monitoring

                if (response.isSuccessful() && response.body() != null) {
                    List<TaskEntity> tasksFromApi = response.body();

                    database.taskDao().getAllTasks().observe(MainActivity.this, existingTasks -> {
                        new Thread(() -> {
                            for (TaskEntity apiTask : tasksFromApi) {
                                boolean exists = false;
                                for (TaskEntity localTask : existingTasks) {
                                    if (apiTask.getId() == localTask.getId()) {
                                        exists = true;
                                        break;
                                    }
                                }
                                if (!exists) {
                                    database.taskDao().insertTask(apiTask);
                                }
                            }
                        }).start();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<TaskEntity>> call, Throwable t) {
                metric.stop();
                Log.e("API_ERROR", "Failed to fetch tasks", t);
                logFirebaseEvent("api_error", "Failed to fetch tasks: " + t.getMessage());
            }
        });
    }

    private void logFirebaseEvent(String eventName, String message) {
        Log.d("FIREBASE_EVENT", "Event: " + eventName + ", Message: " + message);
        Bundle bundle = new Bundle();
        bundle.putString("event_name", eventName);
        bundle.putString("message", message);
        firebaseAnalytics.logEvent(eventName, bundle);
    }
}