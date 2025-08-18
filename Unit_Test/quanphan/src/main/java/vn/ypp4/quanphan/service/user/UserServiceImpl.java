package vn.ypp4.quanphan.service.user;

import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.dto.user.UserUpdateDTO;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponseDTO getUserByUserId(int userId) {
        return null;
    }

    @Override
    public int updateUserProfile(UserUpdateDTO userUpdate) {
        return 0;
    }
}
