package com.arthur.taslmanager.dtos;

import com.arthur.taslmanager.enums.Priority;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    String title;
    String description;
    Priority priority;
}
