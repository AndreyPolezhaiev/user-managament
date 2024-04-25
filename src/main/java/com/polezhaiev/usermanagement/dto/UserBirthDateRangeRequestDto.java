package com.polezhaiev.usermanagement.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserBirthDateRangeRequestDto {
    private LocalDateTime from;
    private LocalDateTime to;
}
