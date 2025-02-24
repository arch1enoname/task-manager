package com.arthur.taslmanager.controllers;

import com.arthur.taslmanager.dtos.CommentDto;
import com.arthur.taslmanager.services.CommentService;
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

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment Management", description = "APIs for managing comments")
@SecurityRequirement(name = "Bearer Authentication")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete a comment", description = "Deletes a comment by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Comment deletion accepted"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteComment(
            @Parameter(description = "ID of the comment to delete", required = true, example = "123")
            @PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Update a comment", description = "Updates the content of a comment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<?> updateComment(
            @Parameter(description = "ID of the comment to update", required = true, example = "123")
            @PathVariable Long commentId,
            @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentId, commentDto), HttpStatus.OK);
    }
}
