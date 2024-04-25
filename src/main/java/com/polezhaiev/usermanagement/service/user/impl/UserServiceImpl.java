package com.polezhaiev.usermanagement.service.user.impl;

import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UpdateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import com.polezhaiev.usermanagement.exception.UserInValidBirthDateException;
import com.polezhaiev.usermanagement.exception.UserRegistrationException;
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
    private final UserMapper userMapper;
    private final UserInMemoryRepository userRepository;

    @Value("$PASS_AGE")
    private final int passAge;
    private static final Long ID = 1L;

    @Override
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        if (requestDto.getBirthDate().isAfter(LocalDateTime.now())
            || requestDto.getBirthDate().isEqual(LocalDateTime.now())) {
            throw new UserInValidBirthDateException("Birthdate can't be current or future");
        }

        if (!checkAge(requestDto.getBirthDate())) {
            throw new UserRegistrationException("Can't register user because the age less than 18");
        }

        User user = userMapper.toModel(requestDto);
        if (userRepository.findAll().isEmpty()) {
            user.setId(ID);

        } else {
            Long userId = userRepository.findAll().getLast().getId() + 1;
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


    private boolean checkAge(LocalDateTime userBirthDate) {
        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.getYear() - userBirthDate.getYear() < passAge) {
            return false;
        }

        if (userBirthDate.getMonthValue() < currentDate.getMonthValue()) {
            return false;
        }

        return userBirthDate.getDayOfMonth() >= currentDate.getDayOfMonth();
    }
}
