package com.arthur.taslmanager.services.impls;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.entities.Comment;
import com.arthur.taslmanager.entities.Role;
import com.arthur.taslmanager.entities.User;
import com.arthur.taslmanager.repositories.CommentRepository;
import com.arthur.taslmanager.services.CommentService;
import com.arthur.taslmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public Comment createComment(CommentDto commentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .date(new Date())
                .author(userService.findByUsername(auth.getName()))
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        if (checkPermissions(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RuntimeException("123");
        }
    }

    @Override
    public Comment updateComment(Long id, CommentDto commentDto) {
        if (checkPermissions(id)) {
            Comment comment = getCommentById(id);
            comment.setText(commentDto.getText());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("123");
        }
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

    private boolean checkPermissions(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Comment comment = getCommentById(id);

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        return roles.contains("ROLE_ADMIN") || comment.getAuthor().equals(user);
    }
}
