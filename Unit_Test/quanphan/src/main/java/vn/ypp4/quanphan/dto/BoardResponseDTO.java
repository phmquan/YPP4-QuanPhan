package vn.ypp4.quanphan.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.domain.Board;

@Getter
@Setter


public class BoardResponseDTO {
    public BoardResponseDTO() {

    }

    public BoardResponseDTO(Board board){
        this.boardId=board.getId();
        this.boardName= board.getBoardName();
        this.backgroundUrl=board.getBackgroundUrl();
    }
    private int boardId;
    private String boardName;
    private String backgroundUrl;
}
