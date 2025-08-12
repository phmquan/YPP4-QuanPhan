package vn.ypp4.quanphan.repository.interf;

import vn.ypp4.quanphan.domain.User;

public interface UserRepository {
    User findUserByUserId(int userId);

    boolean existsById(int userId);

    void createUser(User testUser);
}
