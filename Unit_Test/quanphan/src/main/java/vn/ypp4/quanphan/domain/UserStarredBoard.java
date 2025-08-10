package vn.ypp4.quanphan.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStarredBoard {
    private int userId;
    private int boardId;
    private LocalDateTime createdAt;
    private boolean starredBoardsStatus;
}
