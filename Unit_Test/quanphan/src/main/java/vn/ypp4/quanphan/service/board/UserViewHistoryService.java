package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.BoardResponseDTO;
import vn.ypp4.quanphan.repository.interf.UserViewHistoryRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserViewHistoryService {
    private final UserViewHistoryRepository userViewHistoryRepository;
    public List<BoardResponseDTO> getRecentlyViewedBoardsByUserId(int userId,int numBoardRequested){
        return userViewHistoryRepository.findRecentlyViewedBoardByUserId(userId,numBoardRequested)
                .stream()
                .filter(Objects::nonNull)
                .map(BoardResponseDTO::new)
                .toList();
    }
}
