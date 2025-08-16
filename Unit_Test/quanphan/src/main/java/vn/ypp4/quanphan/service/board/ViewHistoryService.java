package vn.ypp4.quanphan.service.board;

import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;

import java.util.List;

public interface ViewHistoryService {
    public List<BoardResponseDTO> getRecentlyViewedBoardsByUserId(int userId, int numBoardRequested);
}
