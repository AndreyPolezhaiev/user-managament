package com.polezhaiev.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.polezhaiev.usermanagement.dto.CreateUserRequestDto;
import com.polezhaiev.usermanagement.dto.UserBirthDateRangeRequestDto;
import com.polezhaiev.usermanagement.service.user.UserService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("""
            Register user with valid request,
            should return status 200
            """)
    @Test
    public void register_WithValidRequest_ShouldReturnStatus200() throws Exception {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("email@gmailcom");
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));
        requestDto.setFirstName("first name");
        requestDto.setLastName("last name");

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int statusExpected = 200;
        int statusActual = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(statusExpected, statusActual);
    }

    @DisplayName("""
            Register user with inValid request,
            should return status 400
            """)
    @Test
    public void register_WithInValidRequest_ShouldReturnStatus400() throws Exception {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("emailgmailcom");
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));
        requestDto.setFirstName("first name");
        requestDto.setLastName("last name");

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/users")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andReturn();

        int statusExpected = 400;
        int statusActual = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(statusExpected, statusActual);
    }

    @DisplayName("""
            Update user's phone number,
            should return status 200
            """)
    @Test
    public void updatePhoneNumber_WithValidUserId_ShouldReturnStatus200() throws Exception {
        Long id = 1L;

        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setPhoneNumber("1111");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(put("/api/users/phone/{id}", id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int statusExpected = 200;
        int statusActual = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(statusExpected, statusActual);
    }

    @DisplayName("""
            Update all user's fields,
            should return status 200
            """)
    @Test
    public void updateUser_WithValidUserId_ShouldReturnStatus200() throws Exception {
        Long id = 1L;

        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("email@gmailcom");
        requestDto.setBirthDate(LocalDateTime.of(
                2004, 4, 8, 0, 0, 0
        ));
        requestDto.setFirstName("first name");
        requestDto.setLastName("last name");
        requestDto.setPhoneNumber("1111");
        requestDto.setAddress("address");

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(put("/api/users/{id}", id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int statusExpected = 200;
        int statusActual = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(statusExpected, statusActual);
    }

    @DisplayName("""
            Delete user by id,
            should return status 200
            """)
    @Test
    public void deleteById_WithValidUserId_ShouldReturnStatus200() throws Exception {
        Long id = 1L;

        MvcResult mvcResult = mockMvc.perform(delete("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int statusExpected = 200;
        int statusActual = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(statusExpected, statusActual);
    }

    @DisplayName("""
            Find all users by birthdate range,
            should return status 200
            """)
    @Test
    public void searchUsersByBirthDateRange_WithValidBirthDate_ShouldReturnStatus200()
            throws Exception {
        Long id = 1L;

        LocalDateTime from = LocalDateTime.of(
                2003, 4, 8, 0, 0, 0
        );

        LocalDateTime to = LocalDateTime.of(
                2005, 4, 8, 0, 0, 0
        );

        UserBirthDateRangeRequestDto requestDto = new UserBirthDateRangeRequestDto();
        requestDto.setFrom(from);
        requestDto.setTo(to);

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(get("/api/users")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        int statusExpected = 200;
        int statusActual = mvcResult.getResponse().getStatus();

        Assertions.assertEquals(statusExpected, statusActual);
    }
}
