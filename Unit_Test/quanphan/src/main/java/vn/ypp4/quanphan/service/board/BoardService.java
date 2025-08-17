package vn.ypp4.quanphan.service.board;

import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;

import java.util.List;
@Service
public interface BoardService {
    public int createBoard(BoardCreateDTO createBoard);
    public BoardResponseDTO getBoardById(int boardId);
    public List<BoardResponseDTO> getMemberBoardByUserId(int userId);
    public List<BoardResponseDTO> getMemberBoardByUserIdAndWorkspaceId(int userId, int workspaceId);

}
