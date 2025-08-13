package vn.ypp4.quanphan.domain.dto;

import java.util.List;

import lombok.*;
import vn.ypp4.quanphan.domain.dto.board.BoardDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDTO {
    private int id;
    private String workspaceName;
    private String iconUrl;
    private List<BoardDTO> boards;
}
