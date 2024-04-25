package com.polezhaiev.usermanagement.mapper;

import com.polezhaiev.usermanagement.config.MapperConfig;
import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserResponseDto;
import com.polezhaiev.usermanagement.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(CreateUserRequestDto requestDto);
}
