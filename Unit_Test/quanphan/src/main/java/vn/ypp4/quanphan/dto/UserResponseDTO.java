package vn.ypp4.quanphan.dto;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.User;

@Getter
@Setter

public class UserResponseDTO {
    public UserResponseDTO() {
    }

    public UserResponseDTO(User user){
        this.userId=user.getId();
        this.userName=user.getUsername();
        this.fullName=user.getFullName();
        this.avatar=user.getAvatar();
    }
    private int userId;
    private String userName;
    private String fullName;
    private String avatar;
    private String email;
}
