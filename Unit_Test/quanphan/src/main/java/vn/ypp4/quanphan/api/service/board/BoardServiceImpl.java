package vn.ypp4.quanphan.api.service.board;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.api.repository.board.UserBoardRepository;

import java.util.List;

@Service
@NoArgsConstructor
public class BoardServiceImpl implements BoardService {
    @Autowired
    private  UserBoardRepository userBoardRepository;
    @Override
    public List<BoardResponseDTO> getStarredBoardsByUserId(int userId) {
        return userBoardRepository.findStarredBoardsByUserId(userId);
    }

    @Override
    public List<BoardResponseDTO> getViewedBoardsByUserId(int userId) {
        return userBoardRepository.findViewedBoardsByUserId(userId);
    }



    @Override
    public BoardResponseDTO getBoardById(int boardId) {
        return userBoardRepository.findById(boardId);
    }

    @Override
    public List<BoardResponseDTO> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        return userBoardRepository.findStarredBoardsByUserIdAndWorkspaceId(userId,workspaceId);
    }

    @Override
    public List<BoardResponseDTO> getMemberBoardsByUserId(int userId) {
        return userBoardRepository.findMemberBoardsByUserId(userId);
    }

    @Override
    public List<BoardResponseDTO> getMemberBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        return userBoardRepository.findMemberBoardsByUserIdAndWorkspaceId(userId,workspaceId);
    }
}
