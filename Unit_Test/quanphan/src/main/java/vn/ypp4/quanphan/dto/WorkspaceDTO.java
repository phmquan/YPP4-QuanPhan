package vn.ypp4.quanphan.dto;

import java.util.List;

import lombok.*;

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
