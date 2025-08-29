package vn.ypp4.quanphan.api.repository.board;

import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;

import java.util.List;

@Repository

public interface UserBoardRepository {

    List<BoardResponseDTO> findStarredBoardsByUserId(int userId);

    List<BoardResponseDTO> findViewedBoardsByUserId(int userId);

    BoardResponseDTO findById(int boardId);

    List<BoardResponseDTO> findStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId);

    List<BoardResponseDTO> findMemberBoardsByUserId(int userId);

    List<BoardResponseDTO> findMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId);
}
