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
import com.arthur.taslmanager.repositories.TaskRepository;
import com.arthur.taslmanager.services.CommentService;
import com.arthur.taslmanager.services.TaskService;
import com.arthur.taslmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public void changeTaskStatus(Long id, Status status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        taskRepository.save(task);
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
            throw new RuntimeException("123");
        }
    }

    @Override
    public List<Task> findByAuthorUsername(String username) {
        return taskRepository.findByAuthorUsername(username);
    }

    @Override
    public void updatePerformer(PerformerTaskDto performerTaskDto) {
        Task task = getTaskById(performerTaskDto.getTaskId());
        User user = userService.getUserById(performerTaskDto.getPerformerId());
        task.setPerformer(user);
        taskRepository.save(task);
    }

    @Override
    public void addCommentToTask(Long taskId, CommentDto commentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Task task = getTaskById(taskId);
        User user = userService.findByUsername(auth.getName());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        Comment comment = commentService.createComment(commentDto);


        if (roles.contains("ROLE_ADMIN") || task.getPerformer().equals(user)) {
            task.getCommentList().add(comment);
        } else {
            throw new RuntimeException("123");
        }

    }
}
