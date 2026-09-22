package com.practice.efubaccount.user.controller;

import com.practice.efubaccount.user.dto.UserRequestDTO;
import com.practice.efubaccount.user.entity.User;
import com.practice.efubaccount.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody UserRequestDTO requestDTO
    ) {
        User savedUser = userService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
