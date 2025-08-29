package vn.ypp4.quanphan.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.ypp4.quanphan.api.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.api.dto.user.UserUpdateDTO;
import vn.ypp4.quanphan.api.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public UserResponseDTO getUserByUserId(@PathVariable int userId){
        return userService.getUserByUserId(userId);
    }

    @PutMapping("/{id}")
    public int updateUserProfile(UserUpdateDTO userUpdate) {
        return userService.updateUserProfile(userUpdate);
    }
}
