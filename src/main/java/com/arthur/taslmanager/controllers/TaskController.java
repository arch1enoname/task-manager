package com.arthur.taslmanager.controllers;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.dtos.PerformerTaskDto;
import com.arthur.taslmanager.dtos.TaskDto;
import com.arthur.taslmanager.entities.Task;
import com.arthur.taslmanager.enums.Status;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    @Operation(summary = "Delete a task by ID", description = "Marks a task for deletion (asynchronous operation)")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Request accepted for processing"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTaskById(
            @Parameter(description = "ID of the task to delete", required = true, example = "123")
            @PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/createTask")
    @Operation(summary = "Create a new task", description = "Creates a task with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.createNewTask(taskDto), HttpStatus.CREATED);
    }

    @PatchMapping("/updatePerformer")
    @Operation(summary = "Set performer to task", description = "Assigns a performer to a task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Performer assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Task> updatePerformer(@RequestBody PerformerTaskDto performerTaskDto) {
        taskService.updatePerformer(performerTaskDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByAuthorUsername/{username}")
    @Operation(summary = "Get tasks by author username", description = "Retrieves all tasks created by a specific author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTasksByAuthorUsername(@PathVariable String username) {
        return new ResponseEntity<>(taskService.findByAuthorUsername(username), HttpStatus.OK);
    }

    @PostMapping("/addComment/{taskId}")
    @Operation(summary = "Add a comment to a task", description = "Adds a comment to the specified task")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<?> addCommentToTask(@PathVariable Long taskId, @RequestBody CommentDto commentDto) {
        taskService.addCommentToTask(taskId, commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{taskId}")
    @Operation(summary = "Change task status", description = "Updates the status of a task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<?> changeStatus(@PathVariable Long taskId, @RequestBody Status status) {
        return new ResponseEntity<>(taskService.changeTaskStatus(taskId, status), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update a task", description = "Updates the details of a task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTaskById(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTaskById(taskId, taskDto), HttpStatus.OK);
    }

    @GetMapping("/getByPerformerUsername/{username}")
    @Operation(summary = "Get tasks by performer username", description = "Retrieves all tasks assigned to a specific performer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTaskByPerformerUsername(@PathVariable String username) {
        return new ResponseEntity<>(taskService.getTaskByPerformerUsername(username), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by id", description = "Retrieves task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found")
    })
    public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTaskResponseById(taskId), HttpStatus.OK);
    }
}