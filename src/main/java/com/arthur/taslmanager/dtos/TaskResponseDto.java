package com.arthur.taslmanager.dtos;

import com.arthur.taslmanager.entities.User;
import com.arthur.taslmanager.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {
    String title;
    String description;
    User author;
    User performer;
    Status status;
}
