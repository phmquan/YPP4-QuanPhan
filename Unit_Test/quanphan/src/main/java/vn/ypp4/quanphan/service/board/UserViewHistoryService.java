package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.UserViewHistoryRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserViewHistoryService {
    private final UserViewHistoryRepository userViewHistoryRepository;
    private final UserRepository userRepository;
    public Iterator<BoardResponseDTO> getRecentlyViewedBoardsByUserId(int userId, int numBoardRequested){
        if(userRepository.existsById(userId)){
            return userViewHistoryRepository.findRecentlyViewedBoardByUserId(userId,numBoardRequested)
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
