package com.haile.dailytaskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.haile.dailytaskmanager.databinding.ActivityAddTaskBinding;

import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private ActivityAddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddTask.setOnClickListener(v -> {
            String taskName = binding.etTaskName.getText().toString();
            int hour = binding.timePicker.getHour();
            int minute = binding.timePicker.getMinute();

            // Send task data back to the main activity using an Intent
            Intent intent = new Intent();
            intent.putExtra("taskName", taskName);
            intent.putExtra("taskTime", String.format("%02d:%02d", hour, minute));
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
