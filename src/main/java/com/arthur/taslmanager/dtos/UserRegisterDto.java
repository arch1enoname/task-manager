package com.arthur.taslmanager.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDto {
    String username;
    String email;
    String password;
}
