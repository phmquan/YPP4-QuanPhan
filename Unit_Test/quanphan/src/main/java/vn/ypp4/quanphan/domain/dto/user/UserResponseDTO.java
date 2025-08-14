package vn.ypp4.quanphan.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.entity.User;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {
    public UserResponseDTO() {
    }

    public UserResponseDTO(User user){
        this.userId=user.getId();
        this.userName=user.getUsername();
        this.fullName=user.getFullName();
        this.avatar=user.getAvatar();
        this.email=user.getEmail();
    }
    private int userId;
    private String userName;
    private String fullName;
    private String avatar;
    private String email;
}
