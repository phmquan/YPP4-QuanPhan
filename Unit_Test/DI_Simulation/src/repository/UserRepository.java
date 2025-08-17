package repository;


import customDI.annotation.MyComponent;

@MyComponent
public class UserRepository {
    public void findById(int userId) {
        // Logic to find user by ID
        System.out.println("Finding user with ID: " + userId);
    }
}
