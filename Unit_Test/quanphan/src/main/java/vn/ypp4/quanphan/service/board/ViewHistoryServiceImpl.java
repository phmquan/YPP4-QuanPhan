package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.UserViewHistoryRepository;
import vn.ypp4.quanphan.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViewHistoryServiceImpl implements ViewHistoryService {
    private final UserViewHistoryRepository userViewHistoryRepository;
    private final UserRepository userRepository;
    public List<BoardResponseDTO> getRecentlyViewedBoardsByUserId(int userId, int numBoardRequested){
        if(userRepository.existsById(userId)){
            return userViewHistoryRepository.findRecentlyViewedBoardsByUserId(userId,numBoardRequested)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(BoardResponseDTO::new)
                    .toList();
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }
}
