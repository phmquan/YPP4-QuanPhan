package vn.ypp4.quanphan.api.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.api.dto.user.UserUpdateDTO;

@Service
public interface UserService {

    ResponseEntity<UserResponseDTO> getUserByUserId(int userId);

    ResponseEntity<Integer> updateUserProfile(UserUpdateDTO userUpdate);
}
