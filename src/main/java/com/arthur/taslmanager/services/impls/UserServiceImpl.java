package com.arthur.taslmanager.services.impls;

import com.arthur.taslmanager.dtos.UserDto;
import com.arthur.taslmanager.dtos.UserRegisterDto;
import com.arthur.taslmanager.entities.User;
import com.arthur.taslmanager.repositories.UserRepository;
import com.arthur.taslmanager.services.RoleService;
import com.arthur.taslmanager.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return userRepository.findByUsername(username).get();
        } else {
            throw new UsernameNotFoundException(String.format("User with username '%s', does not exist", username));
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Transactional
    public UserDto createNewUser(UserRegisterDto userRegisterDto) {

        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .email(userRegisterDto.getEmail())
                .roles(List.of(roleService.findByName("ROLE_USER")))
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .build();

        userRepository.save(user);
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("123");
        }
    }
}
