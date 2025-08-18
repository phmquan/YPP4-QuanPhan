package vn.ypp4.quanphan.service.user;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.dto.user.UserUpdateDTO;

@Service
public interface UserService {

    UserResponseDTO getUserByUserId(int userId);

    int updateUserProfile(UserUpdateDTO userUpdate);
}
