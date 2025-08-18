package vn.ypp4.quanphan.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserUpdateDTO extends BaseUserDTO {
    private String bio;

    public UserUpdateDTO(int id,String username, String bio) {
        super(id,username);
        this.bio = bio;
    }
}
