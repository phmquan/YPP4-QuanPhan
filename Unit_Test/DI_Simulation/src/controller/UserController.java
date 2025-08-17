package controller;


import customDI.annotation.MyAutowired;
import customDI.annotation.MyComponent;
import service.UserService;

@MyComponent
public class UserController {
    @MyAutowired
    private UserService userService;
    public void getUser(int userId) {
        userService.findUserById(userId);
    }
}
