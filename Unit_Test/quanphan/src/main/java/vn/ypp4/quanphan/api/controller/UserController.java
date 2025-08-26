package vn.ypp4.quanphan.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ypp4.quanphan.api.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.api.dto.user.UserUpdateDTO;
import vn.ypp4.quanphan.api.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserByUserId(@RequestParam int userId) {
        return userService.getUserByUserId(userId);
    }

    @PutMapping
    public ResponseEntity<Integer> updateUserProfile(@RequestBody UserUpdateDTO userUpdate) {
        return userService.updateUserProfile(userUpdate);
    }
}
