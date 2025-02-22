package com.arthur.taslmanager.controllers;

import com.arthur.taslmanager.dtos.TaskDto;
import com.arthur.taslmanager.entities.Task;
import com.arthur.taslmanager.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@Tag(name = "Task Management", description = "APIs for managing tasks")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping("/{taskId}")
    @Operation(
            summary = "Delete a task by ID",
            description = "Marks a task for deletion (asynchronous operation)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Request accepted for processing"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteTaskById(
            @Parameter(
                    description = "ID of the task to delete",
                    required = true,
                    example = "123"
            )
            @PathVariable Long taskId
    ) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/createTask")
    @Operation(
            summary = "Create a new task",
            description = "Creates a task with the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Task> createTask(
            @Parameter(
                    description = "Task data transfer object",
                    required = true
            )
            @RequestBody TaskDto taskDto
    ) {
        return new ResponseEntity<>(taskService.createNewTask(taskDto), HttpStatus.CREATED);
    }

    @PostMapping("/setPerformer/{taskId}/{userId}")
    @Operation(
            summary = "Set performer to task",
            description = "Set performer tto task"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Performer setting successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Task> setPerformer(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.setPerformer(taskId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByAuthorUsername/{username}")
    public ResponseEntity<List<Task>> getTasksByAuthorUsername(@PathVariable String username) {
        return new ResponseEntity<>(taskService.findByAuthorUsername(username), HttpStatus.OK);
    }



}
