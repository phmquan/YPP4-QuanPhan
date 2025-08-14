package vn.ypp4.quanphan.board;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.UserController;
import vn.ypp4.quanphan.domain.dto.user.UserResponseDTO;
import vn.ypp4.quanphan.domain.dto.user.UserUpdateDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TestUser {
    @Autowired
    private UserController userController;
    @Test
    void getUserInforByUserId(){
        //Arrange
        int userId=1;
        UserResponseDTO expected= new UserResponseDTO(1,"quang","Quang Nguyen","pic1.jpg","quang@example.com");
        //Act
        UserResponseDTO result=userController.getUserByUserId(userId);
        //Assert
        assertEquals(expected.getUserId(),result.getUserId());
    }

    @Test
    void updateUserProfile(){
        UserUpdateDTO updateUser=new UserUpdateDTO(1,"Flame","Flame to ash");

        int result=userController.updateUserProfile(updateUser);

        assertEquals(1,result);
    }

}
