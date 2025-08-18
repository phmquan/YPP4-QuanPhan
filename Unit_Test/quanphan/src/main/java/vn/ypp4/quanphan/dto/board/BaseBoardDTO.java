package vn.ypp4.quanphan.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseBoardDTO {
    private String boardName;
    private String backgroundUrl;

    public BaseBoardDTO(String boardName, String backgroundUrl) {
        this.boardName = boardName;
        this.backgroundUrl = backgroundUrl;
    }
}

