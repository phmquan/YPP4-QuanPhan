package vn.ypp4.quanphan.dto.user;


import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.entity.User;


@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO extends BaseUserDTO {
    private String fullName;
    private String email;
    private String avatar;

    public UserResponseDTO(int id, String username, String fullName, String email, String avatar) {
        super(id,username);
        this.fullName = fullName;
        this.email = email;
        this.avatar = avatar;
    }
    public UserResponseDTO(User user){
        super(user.getId(),user.getUsername());
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
    }
}
