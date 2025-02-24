package com.arthur.taslmanager.controllers;

import com.arthur.taslmanager.dtos.JwtRequest;
import com.arthur.taslmanager.dtos.JwtResponse;
import com.arthur.taslmanager.dtos.UserRegisterDto;
import com.arthur.taslmanager.entities.Role;
import com.arthur.taslmanager.exceptions.AppError;
import com.arthur.taslmanager.repositories.RoleRepository;
import com.arthur.taslmanager.services.UserService;
import com.arthur.taslmanager.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(UserService userService,
                          JwtUtils jwtUtils,
                          AuthenticationManager authenticationManager,
                          RoleRepository roleRepository) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Uncorrected login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(userService.createNewUser(userRegisterDto));
    }
}
