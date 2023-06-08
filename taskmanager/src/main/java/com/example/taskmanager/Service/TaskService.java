package com.example.taskmanager.Service;

import org.springframework.stereotype.Service;
import com.example.taskmanager.Repository.*;
import com.example.taskmanager.model.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Task task) {
        // Add any additional logic, validation, or data manipulation before saving the task
        return taskRepository.save(task);
    }

    public Task updateTask(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        // Retrieve the existing task from the TaskRepository using taskId
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if (((Optional<?>) existingTaskOptional).isPresent()) {
            Task existingTask = existingTaskOptional.get();

            // Update the existing task with the properties of the updatedTask
            existingTask.setTaskName(task.getTaskName());
            existingTask.setDescription(task.getDescription());

            // Add any additional logic, validation, or data manipulation before updating the task
            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found");
        }
    }



    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
