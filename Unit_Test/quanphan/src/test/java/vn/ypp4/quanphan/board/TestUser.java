package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.UserController;
import vn.ypp4.quanphan.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.dto.user.UserUpdateDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class TestUser {
    @Autowired
    private UserController userController;
    @Test
    void getUserInforByUserId(){
        //Arrange
        int userId=1;

        //Act
        UserResponseDTO result=userController.getUserByUserId(userId);
        //Assert
        assertNotNull(result);
    }

    @Test
    void updateUserProfile(){

        UserUpdateDTO dummyUser = new UserUpdateDTO(1,"","");
        int result=userController.updateUserProfile(dummyUser);

        assertEquals(1,result);
    }

}
