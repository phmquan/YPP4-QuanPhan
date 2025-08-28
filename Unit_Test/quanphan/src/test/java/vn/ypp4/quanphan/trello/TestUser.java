package vn.ypp4.quanphan.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.ypp4.quanphan.api.controller.UserController;
import vn.ypp4.quanphan.api.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.api.dto.user.UserUpdateDTO;
import vn.ypp4.quanphan.api.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUser {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponseDTO sampleUser;
    private UserUpdateDTO updateUser;

    @BeforeEach
    void setUp() {
        sampleUser = new UserResponseDTO();
        // Set sample data for user - adjust according to your DTO structure

        updateUser = new UserUpdateDTO(1, "", "");
    }

    @Test
    void getUserByUserId_Success() {
        // Arrange
        int userId = 1;
        ResponseEntity<UserResponseDTO> expectedResponse = ResponseEntity.ok(sampleUser);
        when(userService.getUserByUserId(userId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UserResponseDTO> result = userController.getUserByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(userService, times(1)).getUserByUserId(userId);
    }

    @Test
    void getUserByUserId_NotFound() {
        // Arrange
        int userId = 999;
        ResponseEntity<UserResponseDTO> expectedResponse = ResponseEntity.notFound().build();
        when(userService.getUserByUserId(userId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UserResponseDTO> result = userController.getUserByUserId(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userService, times(1)).getUserByUserId(userId);
    }

    @Test
    void updateUserProfile_Success() {
        // Arrange
        ResponseEntity<Integer> expectedResponse = ResponseEntity.ok(1);
        when(userService.updateUserProfile(updateUser)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Integer> result = userController.updateUserProfile(updateUser);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody());
        verify(userService, times(1)).updateUserProfile(updateUser);
    }

    @Test
    void updateUserProfile_BadRequest() {
        // Arrange
        UserUpdateDTO invalidUser = new UserUpdateDTO(0, "", ""); // Invalid ID
        ResponseEntity<Integer> expectedResponse = ResponseEntity.badRequest().build();
        when(userService.updateUserProfile(invalidUser)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Integer> result = userController.updateUserProfile(invalidUser);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(userService, times(1)).updateUserProfile(invalidUser);
    }

    @Test
    void getUserByUserId_InternalServerError() {
        // Arrange
        int userId = 1;
        ResponseEntity<UserResponseDTO> expectedResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        when(userService.getUserByUserId(userId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<UserResponseDTO> result = userController.getUserByUserId(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(userService, times(1)).getUserByUserId(userId);
    }
}
