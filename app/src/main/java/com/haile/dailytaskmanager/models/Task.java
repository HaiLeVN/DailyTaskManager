package com.haile.dailytaskmanager.models;

public class Task {
    private String taskName;
    private String taskTime;

    public Task(String taskName, String taskTime) {
        this.taskName = taskName;
        this.taskTime = taskTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }
}
