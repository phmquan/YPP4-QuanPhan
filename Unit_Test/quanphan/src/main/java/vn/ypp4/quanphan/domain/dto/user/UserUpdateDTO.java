package vn.ypp4.quanphan.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDTO {
    private int id;
    private String username;
    private String bio;
}
