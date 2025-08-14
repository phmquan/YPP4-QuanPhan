package vn.ypp4.quanphan.domain.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateDTO {
    private String boardName;
    private String backgroundUrl;
    private int workspaceId;
}
