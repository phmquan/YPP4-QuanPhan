package vn.ypp4.quanphan.domain.entity;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStarredBoard {
    private int userId;
    private int boardId;
    private LocalDateTime createdAt;
    private boolean starredBoardsStatus;
}
