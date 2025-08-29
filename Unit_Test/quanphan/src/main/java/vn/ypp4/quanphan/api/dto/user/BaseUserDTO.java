package vn.ypp4.quanphan.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseUserDTO {
    private int id;
    private String username;

    public BaseUserDTO(int id, String username) {
        this.username = username;
        this.id = id;
    }
}

