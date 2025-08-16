package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.UserStarredBoardRepository;
import vn.ypp4.quanphan.repository.WorkspaceRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StarredBoardServiceImpl implements StarredBoardService {
    private final UserStarredBoardRepository userStarredBoardRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    public List<BoardResponseDTO> getStarredBoardsByUserId(int userId) {
        if(userRepository.existsById(userId)){
            return userStarredBoardRepository.findStarredBoardsByUserId(userId)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(BoardResponseDTO::new)
                    .toList();
        }
        else{
            throw new NullPointerException("User not found");
        }
    }

    public List<BoardResponseDTO> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        if(userRepository.existsById(userId)&& workspaceRepository.existsByWorkspaceId(workspaceId)){
            return userStarredBoardRepository.findStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(BoardResponseDTO::new)
                    .toList();
        }
        else{
            throw new NullPointerException("User not found");
        }
    }
}
