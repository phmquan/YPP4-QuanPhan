package vn.ypp4.quanphan.api.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.ypp4.quanphan.api.entity.Board;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDTO extends BaseBoardDTO {

    private int id;

    public BoardResponseDTO(String boardName, String backgroundUrl, int id) {
        super(boardName, backgroundUrl);
        this.id = id;
    }

    public BoardResponseDTO(Board board) {
        super(board.getBoardName(), board.getBackgroundUrl());
        this.id = board.getId();
    }
}
