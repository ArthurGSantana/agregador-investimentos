package tech.arthur.agregadorinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.arthur.agregadorinvestimentos.entity.User;
import tech.arthur.agregadorinvestimentos.model.dto.UserDTO;
import tech.arthur.agregadorinvestimentos.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should return a user by id")
        void shouldReturnAUserById() {
            // Arrange
            var user = new User(UUID.randomUUID().toString(), "username", "email", "password");
            doReturn(Optional.of(user)).when(userRepository).findById(stringArgumentCaptor.capture());

            // Act
            var result = userService.GetUserById(user.getId());

            // Assert
            assertNotNull(result);
            assertEquals(user.getId(), result.id());
            assertEquals(user.getUsername(), result.username());
            assertEquals(user.getEmail(), result.email());
        }

        @Test
        @DisplayName("Should return null when user not found")
        void shouldReturnNullWhenUserNotFound() {
            // Arrange
            doReturn(Optional.empty()).when(userRepository).findById(any());
            var input = UUID.randomUUID().toString();

            // Act
            var result = userService.GetUserById(input);

            // Assert
            assertNull(result);
        }
    }

    @Nested
    class listUsers {
        @Test
        @DisplayName("Should return a list of users")
        void shouldReturnAListOfUsers() {
            // Arrange
            var user1 = new User(UUID.randomUUID().toString(), "username1", "email1", "password1");
            var user2 = new User(UUID.randomUUID().toString(), "username2", "email2", "password2");
            doReturn(List.of(user1, user2)).when(userRepository).findAll();

            // Act
            var result = userService.GetAllUsers();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());

            var userDTO1 = result.get(0);
            assertEquals(user1.getId(), userDTO1.id());
            assertEquals(user1.getUsername(), userDTO1.username());
            assertEquals(user1.getEmail(), userDTO1.email());

            var userDTO2 = result.get(1);
            assertEquals(user2.getId(), userDTO2.id());
            assertEquals(user2.getUsername(), userDTO2.username());
            assertEquals(user2.getEmail(), userDTO2.email());
        }

        @Test
        @DisplayName("Should return an empty list when no users found")
        void shouldReturnAnEmptyListWhenNoUsersFound() {
            // Arrange
            doReturn(List.of()).when(userRepository).findAll();

            // Act
            var result = userService.GetAllUsers();

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a new user")
        void shouldCreateANewUser() {
            // Arrange
            var user = new User(UUID.randomUUID().toString(), "username", "email", "password");
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new UserDTO(null, "username", "email", Optional.of("password"));

            // Act
            var result = userService.createUser(input);

            // Assert
            assertNotNull(result);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password().get(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw an exception when error occurs")
        void shouldThrowAnExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new UserDTO(null, "username", "email", Optional.of("password"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class updateUser {
        @Test
        @DisplayName("Should update a user")
        void shouldUpdateAUser() {
            // Arrange
            var user = new User(UUID.randomUUID().toString(), "username", "email", "password");
            doReturn(Optional.of(user)).when(userRepository).findById(stringArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new UserDTO(null, "new username", null, Optional.of("new password"));

            // Act
            userService.updateUser(user.getId(), input);

            // Assert
            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(stringArgumentCaptor.getValue(), user.getId());
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(user.getEmail(), userCaptured.getEmail());
            assertEquals(input.password().get(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should not update a user when user not found")
        void shouldNotUpdateAUserWhenUserNotFound() {
            // Arrange
            doReturn(Optional.empty()).when(userRepository).findById(stringArgumentCaptor.capture());

            var id = UUID.randomUUID().toString();
            var input = new UserDTO(null, "new username", null, Optional.of("new password"));

            // Act
            userService.updateUser(id, input);

            // Assert
            assertEquals(stringArgumentCaptor.getValue(), id);
            verify(userRepository, never()).save(any());
            verify(userRepository, times(1)).findById(any());
        }
    }

    @Nested
    class deleteUser {
        @Test
        @DisplayName("Should delete a user")
        void shouldDeleteAUser() {
            // Arrange
            var user = new User(UUID.randomUUID().toString(), "username", "email", "password");
            doReturn(true).when(userRepository).existsById(stringArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(stringArgumentCaptor.capture());

            // Act
            userService.deleteUser(user.getId());

            // Assert
            var idList = stringArgumentCaptor.getAllValues();
            assertEquals(user.getId(), idList.get(0));
            assertEquals(user.getId(), idList.get(1));

            verify(userRepository, times(1)).existsById(any());
            verify(userRepository, times(1)).deleteById(any());
        }

        @Test
        @DisplayName("Should not delete a user when user not found")
        void shouldNotDeleteAUserWhenUserNotFound() {
            // Arrange
            doReturn(false).when(userRepository).existsById(stringArgumentCaptor.capture());

            var id = UUID.randomUUID().toString();

            // Act
            userService.deleteUser(id);

            // Assert
            assertEquals(id, stringArgumentCaptor.getValue());
            verify(userRepository, times(1)).existsById(any());
            verify(userRepository, never()).deleteById(any());
        }
    }
}