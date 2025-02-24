package com.arthur.taslmanager.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    String text;
    String author;
}
