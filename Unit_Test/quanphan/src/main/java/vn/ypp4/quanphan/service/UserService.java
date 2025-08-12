package vn.ypp4.quanphan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.UserResponseDTO;
import vn.ypp4.quanphan.repository.interf.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserResponseDTO getCurrentUserInforByUserId (int userId){
        return new UserResponseDTO(userRepository.findUserByUserId(userId));
    }
}
