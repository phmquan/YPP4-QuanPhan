package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.UserStarredBoardRepository;
import vn.ypp4.quanphan.repository.WorkspaceRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserStarredBoardService {
    private final UserStarredBoardRepository userStarredBoardRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    public Iterator<BoardResponseDTO> getStarredBoardsByUserId(int userId) {
        if(userRepository.existsById(userId)){
            return userStarredBoardRepository.getStarredBoardsByUserId(userId)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(BoardResponseDTO::new)
                    .toList().iterator();
        }
        else{
            throw new NullPointerException("User not found");
        }
    }

    public Iterator<BoardResponseDTO> getStarredBoardsByUserIdAndWorkspaceId(int userId, int workspaceId) {
        if(userRepository.existsById(userId)&& workspaceRepository.existsByWorkspaceId(workspaceId)){
            return userStarredBoardRepository.getStarredBoardsByUserIdAndWorkspaceId(userId, workspaceId)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(BoardResponseDTO::new)
                    .toList().iterator();
        }
        else{
            throw new NullPointerException("User not found");
        }
    }
}
