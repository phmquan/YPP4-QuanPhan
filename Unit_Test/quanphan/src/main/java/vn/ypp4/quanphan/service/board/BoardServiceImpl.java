package vn.ypp4.quanphan.service.board;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyService;
import vn.ypp4.quanphan.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.board.UserBoardRepository;

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
