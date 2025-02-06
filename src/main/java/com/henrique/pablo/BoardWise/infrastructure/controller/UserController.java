package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.henrique.pablo.BoardWise.application.dto.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.UserResponse;
import com.henrique.pablo.BoardWise.application.dto.UserUpdateRequest;
import com.henrique.pablo.BoardWise.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        UserResponse response = userService.createUser(request);
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping()
    public UserResponse updateUser(@RequestAttribute(name = "id") String id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }
}
