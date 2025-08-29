package vn.ypp4.quanphan.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.api.dto.user.UserUpdateDTO;
import vn.ypp4.quanphan.api.repository.user.UserRepository;
import vn.ypp4.quanphan.api.util.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserResponseDTO getUserByUserId(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public int updateUserProfile(UserUpdateDTO userUpdate) {
        if(userRepository.existsById(userUpdate.getId())){
            return userRepository.update(userUpdate);
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }
}
