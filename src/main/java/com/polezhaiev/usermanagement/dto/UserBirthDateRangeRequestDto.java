package com.polezhaiev.usermanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBirthDateRangeRequestDto {
    private LocalDateTime from;
    private LocalDateTime to;
}
