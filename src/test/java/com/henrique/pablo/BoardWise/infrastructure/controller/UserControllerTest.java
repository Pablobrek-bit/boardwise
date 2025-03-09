package com.henrique.pablo.BoardWise.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henrique.pablo.BoardWise.application.dto.user.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.user.UserResponse;
import com.henrique.pablo.BoardWise.application.dto.user.UserUpdateRequest;
import com.henrique.pablo.BoardWise.application.service.UserService;
import com.henrique.pablo.BoardWise.shared.exception.EmailAlreadyExistsException;
import com.henrique.pablo.BoardWise.shared.exception.HandleException;
import com.henrique.pablo.BoardWise.shared.utils.TokenUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@MockitoSettings(strictness = Strictness.LENIENT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final String userId = UUID.randomUUID().toString();
    private final String userName = "user";
    private final String userEmail = "user@gmail.com";
    private final String userPassword = "Senha123@";

    @BeforeEach
    void setup() {
        userController = new UserController(userService); // Injeta o mock manualmente
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new HandleException())
                .build();
    }

    // -------------------------------------------
    // TESTE: createUser (POST /users)
    // -------------------------------------------
    @Test
    void createUser_ShouldReturnCreatedAndLocationHeader() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(userName, userPassword, userEmail);
        UserResponse response = new UserResponse(userId, userName, userEmail, LocalDateTime.now());

        Mockito.when(userService.createUser(Mockito.any(UserRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location")) // Verifica o header Location
                .andExpect(jsonPath("$.email").value(userEmail));
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenInvalidRequest() throws Exception {
        // Arrange
        UserRequest request = new UserRequest("", "", "");

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenEmailAlreadyExists() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(userName, userPassword, userEmail);
        Mockito.when(userService.createUser(Mockito.any(UserRequest.class)))
                .thenThrow(new EmailAlreadyExistsException("Email already exists"));

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(userName, userPassword, "invalid-email");

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenPasswordIsInvalid() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(userName, "1234", userEmail);

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenNameIsInvalid() throws Exception {
        // Arrange
        UserRequest request = new UserRequest("", userPassword, userEmail);

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenNameIsNull() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(null, userPassword, userEmail);

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenPasswordIsNull() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(userName, null, userEmail);

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_ShouldReturnBadRequestWhenEmailIsEmpty() throws Exception {
        // Arrange
        UserRequest request = new UserRequest(userName, userPassword, "");

        // Act & Assert
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getUser_ShouldReturnUserWhenIdExists() throws Exception {
        // Arrange
        UserResponse response = new UserResponse(userId, userName, userEmail, LocalDateTime.now());

        Mockito.when(userService.getUserById(userId)).thenReturn(response);

        String token = TokenUtil.generateToken(userId);

        // Act & Assert
        mockMvc.perform(get("/users/{userId}", userId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value(userEmail));
    }

    @Test
    void getUser_ShouldReturnUserNotFoundWhenIdNotExists() throws Exception {
        // Arrange
        String id = UUID.randomUUID().toString();
        Mockito.when(userService.getUserById(id)).thenThrow(new RuntimeException("User not found"));

        String token = TokenUtil.generateToken(id);

        // Act & Assert
        mockMvc.perform(get("/users/{userId}", id)
                        .requestAttr("id", userId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_ShouldReturnNoContentWhenUserIsUpdated() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest(userName, userPassword, userEmail);

        String token = TokenUtil.generateToken(userId);

        // Act & Assert
        mockMvc.perform(put("/users")
                        .requestAttr("id", userId)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }

    @Test
    void updateUser_ShouldReturnBadRequestWhenEmailAlreadyExists() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest(userName, userPassword, userEmail);
        Mockito.when(userService.updateUser(userId, request))
                .thenThrow(new EmailAlreadyExistsException("Email already exists"));

        String token = TokenUtil.generateToken(userId);

        // Act & Assert
        mockMvc.perform(put("/users")
                        .requestAttr("id", userId)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_ShouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest(userName, userPassword, "invalid-email");

        String token = TokenUtil.generateToken(userId);

        // Act & Assert
        mockMvc.perform(put("/users")
                        .requestAttr("id", userId)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_ShouldReturnBadRequestWhenPasswordIsInvalid() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest(userName, "1234", userEmail);

        String token = TokenUtil.generateToken(userId);

        // Act & Assert
        mockMvc.perform(put("/users")
                        .requestAttr("id", userId)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_ShouldReturnBadRequestWhenNameIsInvalid() throws Exception {
        // Arrange
        UserUpdateRequest request = new UserUpdateRequest("", userPassword, userEmail);

        String token = TokenUtil.generateToken(userId);

        // Act & Assert
        mockMvc.perform(put("/users")
                        .requestAttr("id", userId)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}