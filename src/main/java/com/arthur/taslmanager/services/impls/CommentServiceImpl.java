package com.arthur.taslmanager.services.impls;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.entities.Comment;
import com.arthur.taslmanager.entities.Task;
import com.arthur.taslmanager.repositories.CommentRepository;
import com.arthur.taslmanager.services.CommentService;
import com.arthur.taslmanager.services.TaskService;
import com.arthur.taslmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TaskService taskService, UserService userService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public void createComment(Long taskId, CommentDto commentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .date(new Date())
                .author(userService.findByUsername(auth.getName()))
                .build();
        commentRepository.save(comment);
        taskService.addCommentToTask(taskId, comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment updateComment(Long id, CommentDto commentDto) {
        Comment comment = getCommentById(id);
        comment.setText(commentDto.getText());
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.get();
        } else {
            throw new RuntimeException("123");
        }
    }
}
