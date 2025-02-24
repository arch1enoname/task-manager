package com.arthur.taslmanager.controllers;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentId, commentDto), HttpStatus.OK);
    }
}
