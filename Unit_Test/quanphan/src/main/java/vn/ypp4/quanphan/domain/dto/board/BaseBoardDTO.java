package vn.ypp4.quanphan.domain.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseBoardDTO {
    private String boardName;
    private String backgroundUrl;

    public BaseBoardDTO(String boardName, String backgroundUrl) {
        this.boardName = boardName;
        this.backgroundUrl = backgroundUrl;
    }
}

