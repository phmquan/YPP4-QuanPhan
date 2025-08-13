package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.UserStarredBoardRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserStarredBoardService {
    private final UserStarredBoardRepository userStarredBoardRepository;
    public List<BoardResponseDTO> getStarredBoardsByUserId(int userId){
        return userStarredBoardRepository.getStarredBoardsByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BoardResponseDTO::new)
                .toList();
    }
}
