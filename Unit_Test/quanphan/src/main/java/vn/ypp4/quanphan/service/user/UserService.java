package vn.ypp4.quanphan.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.domain.dto.user.UserUpdateDTO;
import vn.ypp4.quanphan.domain.entity.User;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.util.exception.UserNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO getUserByUserId (int userId){
        return new UserResponseDTO(userRepository.findUserByUserId(userId));
    }

    public int updateUserProfile(UserUpdateDTO userUpdate) {
        if (userRepository.existsById(userUpdate.getId())) {
            User currentUser = userRepository.findUserByUserId(userUpdate.getId());

            currentUser.setUsername(
                    userUpdate.getUsername() != null ? userUpdate.getUsername() : currentUser.getUsername()
            );
            currentUser.setBio(
                    userUpdate.getBio() != null ? userUpdate.getBio() : currentUser.getBio()
            );

            currentUser.setUpdatedAt(LocalDateTime.now());
            return userRepository.updateUserProfile(currentUser);
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

}
