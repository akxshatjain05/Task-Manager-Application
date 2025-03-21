package com.example.task_manager.ui.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_manager.R;
import com.example.task_manager.database.AppDatabase;
import com.example.task_manager.model.TaskEntity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<TaskEntity> taskList;
    private OnTaskUpdateListener taskUpdateListener;

    public interface OnTaskUpdateListener {
        void onTaskUpdated(TaskEntity updatedTask);
    }

    public TaskAdapter(List<TaskEntity> taskList, OnTaskUpdateListener listener) {
        this.taskList = taskList;
        this.taskUpdateListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskEntity task = taskList.get(position);
        holder.taskName.setText(task.getTitle());
        holder.taskCheckbox.setChecked(task.isCompleted());

        holder.taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(holder.itemView.getContext());
                db.taskDao().insertTask(task);
            }).start();
        });

        holder.taskName.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Edit Task");

            final EditText input = new EditText(v.getContext());
            input.setText(task.getTitle());
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                task.setTitle(input.getText().toString());
                notifyItemChanged(position);
                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(v.getContext());
                    db.taskDao().insertTask(task);
                }).start();
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateTasks(List<TaskEntity> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        CheckBox taskCheckbox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskTitle);
            taskCheckbox = itemView.findViewById(R.id.taskCheckbox);
        }
    }
}