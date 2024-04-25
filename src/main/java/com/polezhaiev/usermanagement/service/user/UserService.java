package com.polezhaiev.usermanagement.service.user;

import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UpdateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    UserResponseDto createUser(CreateUserRequestDto requestDto);

    UserResponseDto updatePhoneNumber(Long id, UpdateUserRequestDto requestDto);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto);

    boolean deleteById(Long id);

    List<UserResponseDto> searchUsersByBirthDateRange(LocalDateTime from, LocalDateTime to);
}
