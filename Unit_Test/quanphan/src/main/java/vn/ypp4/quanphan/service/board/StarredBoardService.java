package vn.ypp4.quanphan.service.board;

import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;

import java.util.List;

public interface StarredBoardService  {
    public List<BoardResponseDTO> getStarredBoardsByUserId(int userId);
    public List<BoardResponseDTO> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId);
}
