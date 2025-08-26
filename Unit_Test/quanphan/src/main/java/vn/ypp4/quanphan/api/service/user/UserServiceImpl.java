package vn.ypp4.quanphan.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponseDTO> getUserByUserId(int userId) {
        try {
            UserResponseDTO user = userRepository.findById(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Integer> updateUserProfile(UserUpdateDTO userUpdate) {
        try {
            if (userRepository.existsById(userUpdate.getId())) {
                int result = userRepository.update(userUpdate);
                return ResponseEntity.ok(result);
            } else {
                throw new UserNotFoundException("User not found");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
