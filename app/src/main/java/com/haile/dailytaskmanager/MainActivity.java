package com.haile.dailytaskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haile.dailytaskmanager.adapter.TaskAdapter;
import com.haile.dailytaskmanager.databinding.ActivityMainBinding;
import com.haile.dailytaskmanager.models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private final int ADD_TASK_REQUEST = 1;
    private ActivityResultLauncher<Intent> addTaskActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize RecyclerView
        taskAdapter = new TaskAdapter(taskList, this, this::onTaskDelete, this::onTaskEdit);
        binding.recyclerViewTasks.setAdapter(taskAdapter);
        binding.recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ActivityResultLauncher
        addTaskActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Handle the result from AddTaskActivity
                        String taskName = result.getData().getStringExtra("taskName");
                        String taskTime = result.getData().getStringExtra("taskTime");

                        // Create a new task and add it to the list
                        Task task = new Task(taskName, taskTime);
                        taskList.add(task);
                        taskAdapter.notifyItemInserted(taskList.size() - 1);
                    }
                }
        );

        // Launch AddTaskActivity when the button is clicked
        binding.btnAddNewTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskActivityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String taskName = data.getStringExtra("taskName");
            String taskTime = data.getStringExtra("taskTime");
            taskList.add(new Task(taskName, taskTime));
            taskAdapter.notifyDataSetChanged();
        }
    }

    private void onTaskDelete(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }

    private void onTaskEdit(int position) {
        // Chua lam
    }
}