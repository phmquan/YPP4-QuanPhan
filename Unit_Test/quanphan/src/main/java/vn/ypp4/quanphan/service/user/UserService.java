package vn.ypp4.quanphan.service.user;

import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.domain.dto.user.UserUpdateDTO;

public interface UserService {
    public UserResponseDTO getUserByUserId (int userId);
    public int updateUserProfile(UserUpdateDTO userUpdate);

}
