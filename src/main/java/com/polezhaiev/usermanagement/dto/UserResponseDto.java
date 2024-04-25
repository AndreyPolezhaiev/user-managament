package com.polezhaiev.usermanagement.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private String address;
    private String phoneNumber;
}
