package vn.ypp4.quanphan.domain.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateDTO extends BaseBoardDTO {
    private int workspaceId;

    public BoardCreateDTO(String boardName, String backgroundUrl, int workspaceId) {
        super(boardName, backgroundUrl);
        this.workspaceId = workspaceId;
    }
}
