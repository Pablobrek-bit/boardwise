package com.henrique.pablo.BoardWise.application.service;

import com.henrique.pablo.BoardWise.application.dto.user.UserRequest;
import com.henrique.pablo.BoardWise.application.dto.user.UserResponse;
import com.henrique.pablo.BoardWise.application.dto.user.UserUpdateRequest;
import com.henrique.pablo.BoardWise.domain.model.RoleModel;
import com.henrique.pablo.BoardWise.domain.model.UserModel;
import com.henrique.pablo.BoardWise.domain.repository.IUserRepository;
import com.henrique.pablo.BoardWise.shared.exception.EmailAlreadyExistsException;
import com.henrique.pablo.BoardWise.shared.exception.IdNotFoundException;
import com.henrique.pablo.BoardWise.shared.exception.RoleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    private UserModel userModel;
    private String existingId;
    private String nonExistingId;
    private String existingEmail;
    private String nonExistingEmail;

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        this.existingId = UUID.randomUUID().toString();
        this.nonExistingId = "non-existing-id";
        this.existingEmail = "test@gmail.com";
        this.nonExistingEmail = "non-existing-email";

        userModel = UserModel.builder()
                .id(existingId)
                .username("test")
                .passwordHash("12345678")
                .email(this.existingEmail)
                .build();

        RoleModel roleModel = RoleModel.builder().id(1).name("ROLE_USER").build();

        // Mocking the UserRepository
        // Mocking save method
        Mockito.when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(userModel);

        // Mocking findById method
        Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(userModel));
        Mockito.when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Mocking findByEmail method
        Mockito.when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));
        Mockito.when(userRepository.findByEmail(this.nonExistingEmail)).thenReturn(Optional.empty());

        // Mock do passwordEncoder
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Mock do roleService
        Mockito.when(roleService.findByName("ROLE_USER")).thenReturn(Optional.of(roleModel));
    }

    @Test
    void createUser_MustBeAbleToCreateANewUser() {
        UserRequest request = new UserRequest("test", "12345678", this.nonExistingEmail);

        UserResponse savedUser = userService.createUser(request);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("test", savedUser.username());

        Mockito.verify(userRepository).save(Mockito.argThat(user -> user.getUsername().equals(request.username())));
        Mockito.verify(passwordEncoder).encode(request.password());
        Mockito.verify(roleService).findByName("ROLE_USER");
    }

    @Test
    void createUser_MustThrowEmailAlreadyExistsExceptionWhenEmailIsAlreadyInUse(){
        UserRequest request = new UserRequest("newuser", "12345678", existingEmail);

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(request));

        // Verifications
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(existingEmail);
        Mockito.verify(userRepository).findByEmail(existingEmail);
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserModel.class));
    }

    @Test
    void createUser_shouldThrowRoleNotFoundExceptionWhenRoleDoesNotExist() {
        Mockito.when(roleService.findByName("ROLE_USER")).thenReturn(Optional.empty());

        UserRequest request = new UserRequest("test", "password", "test@example.com");

        assertThrows(RoleNotFoundException.class, () -> userService.createUser(request));

        Mockito.verify(roleService).findByName("ROLE_USER");
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getUserById_MustBeAbleToFindAUserById() {
        UserResponse user = userService.getUserById(existingId);

        Assertions.assertNotNull(user);
        Assertions.assertEquals("test", user.username());

        Mockito.verify(userRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    void getUserById_MustThrowIdNotFoundExceptionWhenUserIsNotFound() {
        Assertions.assertThrows(IdNotFoundException.class, () -> userService.getUserById(nonExistingId));
    }

    @Test
    void updateUser_MustBeAbleToUpdateAUser() {
        userModel = UserModel.builder()
                .username("newusername")
                .passwordHash("newpassword")
                .email("newemail@gmail.com")
                .build();

        UserResponse updatedUser = userService.updateUser(existingId, new UserUpdateRequest("newusername", "newpassword", "newemail@gmail.com"));

        // Assertions
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("newusername", updatedUser.username());

        // Verifications
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserModel.class));
        Mockito.verify(userRepository).findById(existingId);
        Mockito.verify(userRepository).save(Mockito.any(UserModel.class));
        Mockito.verify(passwordEncoder).encode("newpassword");
    }

    @Test
    void updateUser_MustThrowIdNotFoundExceptionWhenUserIsNotFound() {
        Assertions.assertThrows(IdNotFoundException.class, () -> userService.updateUser(nonExistingId, new UserUpdateRequest("newusername", "newpassword", "newemail@gmail.com")));
    }

    @Test
    void updateUser_MustThrowEmailAlreadyExistsExceptionWhenEmailIsAlreadyInUse(){
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.updateUser(existingId, new UserUpdateRequest("newusername", "newpassword", this.existingEmail)));

        // Verifications
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(this.existingEmail);
        Mockito.verify(userRepository).findByEmail(this.existingEmail);
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserModel.class));
    }
}