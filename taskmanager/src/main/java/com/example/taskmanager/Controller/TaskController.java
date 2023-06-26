package com.example.taskmanager.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskmanager.Service.*;
import com.example.taskmanager.model.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private  TaskService taskService;
    @Autowired
    private  UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task ){
        // Fetch the User object using the userId provided in the request
        User user = userService.getUserById(task.getUser().getId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Set the fetched User object to the task
        task.setUser(user);

        // Save the task
        Task createdTask = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
     taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    // Other endpoints for task-related operations

}

