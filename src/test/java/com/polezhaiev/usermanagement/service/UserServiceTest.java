package com.polezhaiev.usermanagement.service;

import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import com.polezhaiev.usermanagement.mapper.UserMapper;
import com.polezhaiev.usermanagement.repository.user.UserInMemoryRepository;
import com.polezhaiev.usermanagement.service.user.impl.UserServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserInMemoryRepository userRepository;

    @Test
    @DisplayName("""
            Correct creating a user,
            should return created user
            """)
    public void createUser_WithValidRequest_ShouldReturnCreatedUser() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setAddress("address");
        requestDto.setEmail("email@gmail.com");
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));
        requestDto.setFirstName("name");
        requestDto.setLastName("last");
        requestDto.setPhoneNumber("11111111");

        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);
        expected.setAddress("address");
        expected.setEmail("email@gmail.com");
        expected.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));
        expected.setFirstName("name");
        expected.setLastName("last");
        expected.setPhoneNumber("11111111");

        UserResponseDto actual = userService.createUser(requestDto);
        Assertions.assertEquals(expected, actual);
    }

}
