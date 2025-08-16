package vn.ypp4.quanphan.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.ypp4.quanphan.domain.entity.Board;

@Getter
@Setter

public class BoardResponseDTO extends BaseBoardDTO {

    private int id;

    public BoardResponseDTO(String boardName, String backgroundUrl, int id) {
        super(boardName, backgroundUrl);
        this.id = id;
    }
    public BoardResponseDTO(Board board){
        super(board.getBoardName(),  board.getBackgroundUrl());
        this.id = board.getId();
    }
}
