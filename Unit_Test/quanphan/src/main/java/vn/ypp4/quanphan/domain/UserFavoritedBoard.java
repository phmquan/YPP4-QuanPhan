package vn.ypp4.quanphan.domain;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFavoritedBoard {
    private int userId;
    private int boardId;
    private LocalDateTime createdAt;
    private boolean starredBoardsStatus;
}
