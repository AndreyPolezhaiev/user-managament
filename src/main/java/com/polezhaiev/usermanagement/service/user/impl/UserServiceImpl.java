package com.polezhaiev.usermanagement.service.user.impl;

import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UpdateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import com.polezhaiev.usermanagement.exception.app.UserInValidBirthDateException;
import com.polezhaiev.usermanagement.exception.app.UserRegistrationException;
import com.polezhaiev.usermanagement.mapper.UserMapper;
import com.polezhaiev.usermanagement.model.User;
import com.polezhaiev.usermanagement.repository.user.UserInMemoryRepository;
import com.polezhaiev.usermanagement.service.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Long ID = 1L;

    private final UserMapper userMapper;
    private final UserInMemoryRepository userRepository;

    @Value("${pass.age}")
    private int passAge;

    @Override
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        checkBirthDate(requestDto.getBirthDate());

        User user = userMapper.toModel(requestDto);
        if (userRepository.findAll().isEmpty()) {
            user.setId(ID);

        } else {
            Long userId = userRepository.findAll().stream()
                    .mapToLong(User::getId)
                    .max()
                    .orElse(0L) + 1;
            user.setId(userId);
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updatePhoneNumber(Long id, UpdateUserRequestDto requestDto) {
        User user = userRepository.findById(id);
        user.setPhoneNumber(requestDto.getPhoneNumber());

        userRepository.deleteById(id);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        checkBirthDate(requestDto.getBirthDate());

        User user = userRepository.findById(id);
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setEmail(requestDto.getEmail());
        user.setAddress(requestDto.getAddress());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setBirthDate(requestDto.getBirthDate());

        userRepository.deleteById(id);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public boolean deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> searchUsersByBirthDateRange(LocalDateTime from, LocalDateTime to) {
        return userRepository.findAll().stream()
                .filter(u -> u.getBirthDate().isAfter(from) && u.getBirthDate().isBefore(to))
                .map(userMapper::toDto)
                .toList();
    }

    private void checkBirthDate(LocalDateTime userBirthDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        if (userBirthDate.isAfter(currentDate)
                || userBirthDate.isEqual(currentDate)) {
            throw new UserInValidBirthDateException(
                    "Birthdate can't be current or future");
        }

        if (currentDate.getYear() - userBirthDate.getYear() < passAge) {
            throw new UserRegistrationException(
                    "Can't register user because the age less than 18");

        } else if (currentDate.getYear() - userBirthDate.getYear() == passAge) {
            if (userBirthDate.getMonthValue() > currentDate.getMonthValue()) {
                throw new UserRegistrationException(
                        "Can't register user because the age less than 18");

            } else if (userBirthDate.getDayOfMonth() > currentDate.getDayOfMonth()) {
                throw new UserRegistrationException(
                        "Can't register user because the age less than 18");
            }
        }
    }
}
