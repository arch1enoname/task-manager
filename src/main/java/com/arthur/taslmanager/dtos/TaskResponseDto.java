package com.arthur.taslmanager.dtos;

import com.arthur.taslmanager.enums.Priority;
import com.arthur.taslmanager.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {
    String title;
    String description;
    String authorUsername;
    String performerUsername;
    Status status;
    Priority priority;
    List<CommentResponseDto> commentList;
}
