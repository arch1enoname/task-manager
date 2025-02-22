package com.arthur.taslmanager.services;

import com.arthur.taslmanager.dtos.UserDto;
import com.arthur.taslmanager.dtos.UserRegisterDto;
import com.arthur.taslmanager.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createNewUser(UserRegisterDto userRegisterDto);
    User findByUsername(String username);
    User getUserById(Long id);
}
