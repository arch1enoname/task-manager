package com.arthur.taslmanager.services;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.entities.Comment;

public interface CommentService {
    void createComment(Long taskId, CommentDto commentDto);
    void deleteCommentById(Long id);
    Comment updateComment(Long id, CommentDto commentDto);
    Comment getCommentById(Long id);
}
