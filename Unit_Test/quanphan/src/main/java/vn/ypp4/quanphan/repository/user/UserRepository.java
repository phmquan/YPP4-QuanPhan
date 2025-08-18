package vn.ypp4.quanphan.repository.user;

import vn.ypp4.quanphan.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.dto.user.UserUpdateDTO;

public interface UserRepository {
    UserResponseDTO findById(int userId);

    boolean existsById(int id);

    int update(UserUpdateDTO userUpdate);
}
