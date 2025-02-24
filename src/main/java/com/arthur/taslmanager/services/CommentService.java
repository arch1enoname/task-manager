package com.arthur.taslmanager.services;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.dtos.CommentResponseDto;
import com.arthur.taslmanager.entities.Comment;

public interface CommentService {
    Comment createComment(CommentDto commentDto);
    void deleteCommentById(Long id);
    Comment updateComment(Long id, CommentDto commentDto);
    Comment getCommentById(Long id);
    CommentResponseDto getCommentResponse(Comment comment);
}
