package vn.ypp4.quanphan.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class BaseUserDTO {
    private int id;
    private String username;

    public BaseUserDTO(int id, String username) {
        this.username = username;
        this.id = id;
    }
}

