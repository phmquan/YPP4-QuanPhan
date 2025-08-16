package vn.ypp4.quanphan.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
