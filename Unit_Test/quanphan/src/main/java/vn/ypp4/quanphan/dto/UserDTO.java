package vn.ypp4.quanphan.dto;

import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.User;

@Getter
@Setter
public class UserDTO {

    public UserDTO(User user) {
        id = user.getId();
        userName = user.getUsername();
        avatarUrl = user.getAvatar();
    }

    private int id;
    private String userName;
    private String avatarUrl;
}
