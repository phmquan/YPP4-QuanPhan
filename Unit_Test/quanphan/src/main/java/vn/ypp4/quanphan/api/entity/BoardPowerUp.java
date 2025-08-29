package vn.ypp4.quanphan.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardPowerUp {
    private int boardId;
    private int powerUpId;
    private boolean boardPowerUpStatus;
}
