package service;


import customDI.annotation.MyAutowired;
import customDI.annotation.MyComponent;
import repository.UserRepository;

@MyComponent
public class UserService {
    @MyAutowired
    private UserRepository userRepository;

    public void findUserById(int userId) {
        userRepository.findById(userId);
    }

}
