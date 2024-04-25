package com.polezhaiev.usermanagement.controller;

import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UpdateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserBirthDateRangeRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import com.polezhaiev.usermanagement.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for users managing")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Register a new user",
            description = "Register a new user")
    @PostMapping
    public UserResponseDto register(@RequestBody @Valid CreateUserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @Operation(summary = "Update user's phone number",
            description = "Update user's phone number")
    @PutMapping("/phone/{id}")
    public UserResponseDto updatePhoneNumber(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRequestDto requestDto) {
        return userService.updatePhoneNumber(id, requestDto);
    }

    @Operation(summary = "Update the user",
            description = "Update the user")
    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @Operation(summary = "Delete the user by id",
            description = "Delete the user by id")
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @Operation(summary = "Search users by birthdate range",
            description = "Search users by birthdate range")
    @GetMapping
    public List<UserResponseDto> searchUsersByBirthDateRange(
            @RequestBody UserBirthDateRangeRequestDto requestDto) {
        return userService.searchUsersByBirthDateRange(requestDto.getFrom(), requestDto.getTo());
    }
}
