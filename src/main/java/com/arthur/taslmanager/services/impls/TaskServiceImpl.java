package com.arthur.taslmanager.services.impls;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.dtos.PerformerTaskDto;
import com.arthur.taslmanager.dtos.TaskDto;
import com.arthur.taslmanager.dtos.TaskResponseDto;
import com.arthur.taslmanager.entities.Comment;
import com.arthur.taslmanager.entities.Role;
import com.arthur.taslmanager.entities.Task;
import com.arthur.taslmanager.entities.User;
import com.arthur.taslmanager.enums.Status;
import com.arthur.taslmanager.exceptions.InvalidUserException;
import com.arthur.taslmanager.exceptions.TaskNotFoundException;
import com.arthur.taslmanager.repositories.TaskRepository;
import com.arthur.taslmanager.services.CommentService;
import com.arthur.taslmanager.services.TaskService;
import com.arthur.taslmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, CommentService commentService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

    @Override
    public Task createNewTask(TaskDto taskdto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Task task = Task.builder()
                .title(taskdto.getTitle())
                .description(taskdto.getDescription())
                .status(Status.NEW)
                .author(userService.findByUsername(auth.getName()))
                .priority(taskdto.getPriority())
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task changeTaskStatus(Long id, Status status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDto updateTaskById(Long id, TaskDto taskDto) {
        return null;
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    @Override
    public List<TaskResponseDto> findByAuthorUsername(String username) {
        List<Task> tasks = taskRepository.findByAuthorUsername(username);
        return fromEntityToDto(tasks);
    }

    @Override
    public void updatePerformer(PerformerTaskDto performerTaskDto) {
        Task task = getTaskById(performerTaskDto.getTaskId());
        User user = userService.getUserById(performerTaskDto.getPerformerId());
        task.setPerformer(user);
        taskRepository.save(task);
    }

    @Override
    public void updateTaskStatus(Long id, Status status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        taskRepository.save(task);
    }

    @Override
    public List<TaskResponseDto> getTaskByPerformerUsername(String username) {
        List<Task> tasks = taskRepository.findByPerformerUsername(username);
        return fromEntityToDto(tasks);


    }

    @Override
    public TaskResponseDto getTaskResponseById(Long id) {
        Task task = getTaskById(id);
        return TaskResponseDto.builder()
                .title(task.getTitle())
                .performerUsername(task.getPerformer().getUsername())
                .authorUsername(task.getAuthor().getUsername())
                .status(task.getStatus())
                .description(task.getDescription())
                .priority(task.getPriority())
                .commentList(task.getCommentList().stream().map(commentService::getCommentResponse).toList())
                .build();
    }

    @Override
    public void addCommentToTask(Long taskId, CommentDto commentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Task task = getTaskById(taskId);
        User user = userService.findByUsername(auth.getName());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        if (roles.contains("ROLE_ADMIN") || task.getPerformer().equals(user)) {
            Comment comment = commentService.createComment(commentDto);
            task.getCommentList().add(comment);
            taskRepository.save(task);
        } else {
            throw new InvalidUserException("User don`t have permission");
        }
    }

    private List<TaskResponseDto> fromEntityToDto(List<Task> tasks) {
        List<TaskResponseDto> taskResponse = new ArrayList<>();

        for(Task task : tasks) {
            taskResponse.add(
                    TaskResponseDto.builder()
                            .title(task.getTitle())
                            .performerUsername(task.getPerformer().getUsername())
                            .authorUsername(task.getAuthor().getUsername())
                            .status(task.getStatus())
                            .description(task.getDescription())
                            .priority(task.getPriority())
                            .commentList(task.getCommentList().stream().map(commentService::getCommentResponse).toList())
                            .build()
            );
        }
        return taskResponse;
    }
}
