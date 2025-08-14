package vn.ypp4.quanphan.domain.dto.user;

import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.entity.User;

@Getter
@Setter
public class UserDTO {

    public UserDTO(User user) {
        id = user.getId();
        userName = user.getUsername();
        avatar = user.getAvatar();
    }

    private int id;
    private String userName;
    private String avatar;
}
