package vn.ypp4.quanphan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO getUserByUserId (int userId){
        return new UserResponseDTO(userRepository.findUserByUserId(userId));
    }
}
