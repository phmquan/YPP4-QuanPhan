package vn.ypp4.quanphan.domain.dto.board;

import lombok.*;
import vn.ypp4.quanphan.domain.Board;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private int id;
    private String name;
    private String backgroundUrl;

    public BoardDTO(Board board) {
        this.id= board.getId();
        this.name= board.getBoardName();
        this.backgroundUrl= board.getBackgroundUrl();
    }
}
