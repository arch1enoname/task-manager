package com.arthur.taslmanager.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformerTaskDto {
    Long taskId;
    Long performerId;
}
