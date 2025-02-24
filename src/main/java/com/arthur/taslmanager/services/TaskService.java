package com.arthur.taslmanager.services;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.dtos.PerformerTaskDto;
import com.arthur.taslmanager.dtos.TaskDto;
import com.arthur.taslmanager.dtos.TaskResponseDto;
import com.arthur.taslmanager.entities.Comment;
import com.arthur.taslmanager.entities.Task;
import com.arthur.taslmanager.enums.Status;

import java.util.List;

public interface TaskService {
    Task createNewTask(TaskDto taskdto);
    void changeTaskStatus(Long id, Status status);
    void deleteTaskById(Long id);
    TaskResponseDto updateTaskById(Long id, TaskDto taskDto);
    Task getTaskById(Long id);
    List<Task> findByAuthorUsername(String username);
    void addCommentToTask(Long id, CommentDto commentDto);
    void updatePerformer(PerformerTaskDto performerTaskDto);
}
