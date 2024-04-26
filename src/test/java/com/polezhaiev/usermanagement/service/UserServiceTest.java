package com.polezhaiev.usermanagement.service;

import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UpdateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import com.polezhaiev.usermanagement.mapper.UserMapper;
import com.polezhaiev.usermanagement.model.User;
import com.polezhaiev.usermanagement.repository.user.UserInMemoryRepository;
import com.polezhaiev.usermanagement.service.user.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserInMemoryRepository userRepository;

    @BeforeEach
    void beforeAll() {
        ReflectionTestUtils.setField(userService, "passAge", 18);
    }

    @Test
    @DisplayName("""
            Correct creating a user,
            should return created user
            """)
    public void createUser_WithValidRequest_ShouldReturnCreatedUser() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        User user = new User();
        user.setId(1L);
        user.setBirthDate(requestDto.getBirthDate());

        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);
        expected.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        Mockito.when(userMapper.toModel(any())).thenReturn(user);
        Mockito.when(userMapper.toDto(any())).thenReturn(expected);

        UserResponseDto actual = userService.createUser(requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Correct creating a second user,
            should return created user
            """)
    public void createUser_WithSecondValidRequest_ShouldReturnCreatedUser() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        User user = new User();
        user.setId(1L);
        user.setBirthDate(requestDto.getBirthDate());

        List<User> users = List.of(user);

        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);
        expected.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userMapper.toModel(any())).thenReturn(user);
        Mockito.when(userMapper.toDto(any())).thenReturn(expected);

        UserResponseDto actual = userService.createUser(requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Creating a user,
            should throw UserInValidBirthDateException
            """)
    public void createUser_WithInValidBirthDate_ShouldThrowUserInValidBirthDateException() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setBirthDate(LocalDateTime.of(
                2025, 4, 8, 0, 0, 0
        ));

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.createUser(requestDto)
        );

        String expected = "Birthdate can't be current or future";
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Creating a user,
            should throw UserRegistrationException
            """)
    public void createUser_WithInValidRequest_ShouldThrowUserInValidBirthDateException() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setBirthDate(LocalDateTime.of(
                2013, 4, 8, 0, 0, 0
        ));

        RuntimeException exceptionOfYear = Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.createUser(requestDto)
        );

        String expectedOfYear = "Can't register user because the age less than 18";
        String actualOfYear = exceptionOfYear.getMessage();
        Assertions.assertEquals(expectedOfYear, actualOfYear);

        requestDto.setBirthDate(LocalDateTime.of(
                2006, 5, 8, 0, 0, 0
        ));

        RuntimeException exceptionOfMonth = Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.createUser(requestDto)
        );

        String expectedOfMonth = "Can't register user because the age less than 18";
        String actualOfMonth = exceptionOfMonth.getMessage();
        Assertions.assertEquals(expectedOfMonth, actualOfMonth);

        requestDto.setBirthDate(LocalDateTime.of(
                2006, 4, 27, 0, 0, 0
        ));

        RuntimeException exceptionOfDay = Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.createUser(requestDto)
        );

        String expectedOfDay = "Can't register user because the age less than 18";
        String actualOfDay = exceptionOfDay.getMessage();
        Assertions.assertEquals(expectedOfDay, actualOfDay);
    }

    @Test
    @DisplayName("""
            Update user phone number,
            should return updated user
            """)
    public void updatePhoneNumber_WithValidId_ShouldReturnUpdatedUser() {
        Long id = 1L;
        User user = new User();
        user.setId(1L);
        user.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        UpdateUserRequestDto requestDto = new UpdateUserRequestDto();
        requestDto.setPhoneNumber("1111");

        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);
        expected.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));
        expected.setPhoneNumber(requestDto.getPhoneNumber());

        Mockito.when(userRepository.findById(any())).thenReturn(user);
        Mockito.when(userMapper.toDto(any())).thenReturn(expected);

        UserResponseDto actual = userService.updatePhoneNumber(id, requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Update all or some user's fields,
            should return updated user
            """)
    public void updateUser_WithValidRequest_ShouldReturnUpdatedUser() {
        Long id = 1L;
        User user = new User();
        user.setId(1L);
        user.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        UpdateUserRequestDto requestDto = new UpdateUserRequestDto();
        requestDto.setPhoneNumber("1111");
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 27, 0, 0, 0
        ));
        requestDto.setLastName("johnson");
        requestDto.setFirstName("john");

        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);
        expected.setBirthDate(requestDto.getBirthDate());
        expected.setPhoneNumber(requestDto.getPhoneNumber());
        expected.setFirstName(requestDto.getFirstName());
        expected.setLastName(requestDto.getLastName());

        Mockito.when(userRepository.findById(any())).thenReturn(user);
        Mockito.when(userMapper.toDto(any())).thenReturn(expected);

        UserResponseDto actual = userService.updateUser(id, requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Update all or some user's fields,
            should throw UserInValidBirthDateException
            """)
    public void updateUser_WithInValidBirthDate_ShouldThrowUserInValidBirthDateException() {
        Long id = 1L;
        User user = new User();
        user.setId(1L);
        user.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        UpdateUserRequestDto requestDto = new UpdateUserRequestDto();
        requestDto.setPhoneNumber("1111");
        requestDto.setBirthDate(LocalDateTime.of(
                2010, 4, 27, 0, 0, 0
        ));
        requestDto.setLastName("johnson");
        requestDto.setFirstName("john");

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.updateUser(id, requestDto));

        String expected = "Can't register user because the age less than 18";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Delete the user by id
        """)
    public void deleteById_WithValidId_ShouldDeleteUser() {
        Long id = 1L;
        userService.deleteById(id);

        Mockito.verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("""
            Search users by birthdate range,
            should return all valid users
            """)
    public void searchUsersByBirthDateRange_WithValidRequest_ShouldReturnAllValidUsers() {
        LocalDateTime from = LocalDateTime.of(
                2003, 4, 8, 0, 0, 0
        );

        LocalDateTime to = LocalDateTime.of(
                2005, 4, 8, 0, 0, 0
        );

        User user = new User();
        user.setId(1L);
        user.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        List<User> users = List.of(user);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));

        List<UserResponseDto> expected = List.of(responseDto);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userMapper.toDto(any())).thenReturn(responseDto);

        List<UserResponseDto> actual = userService.searchUsersByBirthDateRange(from, to);
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }
}
