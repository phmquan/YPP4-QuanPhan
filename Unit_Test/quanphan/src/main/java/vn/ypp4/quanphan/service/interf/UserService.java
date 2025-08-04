package vn.ypp4.quanphan.service.interf;

import java.time.Instant;
import java.util.List;

import vn.ypp4.quanphan.domain.User;

public interface UserService {
    User createUser(String username, String bio, String email, Instant lastActive, Instant createdAt,
            String pictureUrl);

    User getUserById(int id);

    User getUserByEmail(String email);

    List<User> getAllUser();

    int UpdateUserById(int id, String username, String bio, String pictureUrl);

    int DeleteUserById(int id);
}
