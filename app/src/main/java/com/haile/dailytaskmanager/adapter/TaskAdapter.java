package com.haile.dailytaskmanager.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haile.dailytaskmanager.databinding.ItemTaskBinding;
import com.haile.dailytaskmanager.models.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;
    private OnTaskDeleteListener deleteListener;
    private OnTaskEditListener editListener;
    private Context context;

    public interface OnTaskDeleteListener {
        void onTaskDelete(int position);
    }

    public interface OnTaskEditListener {
        void onTaskEdit(int position);
    }

    public TaskAdapter(ArrayList<Task> taskList, Context context, OnTaskDeleteListener deleteListener, OnTaskEditListener editListener) {
        this.taskList = taskList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTaskBinding binding = ItemTaskBinding.inflate(inflater, parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.binding.tvTaskName.setText(task.getTaskName());
        holder.binding.tvTaskTime.setText(task.getTaskTime());

        holder.binding.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(position);
        });
        holder.binding.btnEdit.setOnClickListener(v -> editListener.onTaskEdit(position));
    }

    private void showDeleteConfirmationDialog(int position) {
        // Create an AlertDialog for confirmation
        new AlertDialog.Builder(context)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Delete the task after confirmation
                    deleteListener.onTaskDelete(position);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())  // Dismiss the dialog if "No" is clicked
                .create()
                .show();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private ItemTaskBinding binding;

        public TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
