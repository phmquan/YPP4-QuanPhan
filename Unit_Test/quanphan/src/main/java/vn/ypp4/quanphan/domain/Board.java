package vn.ypp4.quanphan.domain;

import java.time.LocalDateTime;

import lombok.*;
import vn.ypp4.quanphan.util.constant.BoardStatusEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private int id;
    private String boardName;
    private String boardDescription;
    private LocalDateTime createdAt;
    private int createdBy;
    private String backgroundUrl;
    private int workspaceId;
    private BoardStatusEnum boardStatus;
    private LocalDateTime updatedAt;
    private int updatedBy;
}
