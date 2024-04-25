package com.polezhaiev.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @Email
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDateTime birthDate;
    private String address;
    private String phoneNumber;
}
