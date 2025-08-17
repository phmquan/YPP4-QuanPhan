package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.UserViewHistoryRepository;
import vn.ypp4.quanphan.service.dto.EntityToDtoMapper;
import vn.ypp4.quanphan.util.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewHistoryServiceImpl implements ViewHistoryService {
    private final UserViewHistoryRepository userViewHistoryRepository;
    private final UserRepository userRepository;
    public List<BoardResponseDTO> getRecentlyViewedBoardsByUserId(int userId, int numBoardRequested){
        if(userRepository.existsById(userId)){
            return EntityToDtoMapper.mapToDto(userViewHistoryRepository.findRecentlyViewedBoardsByUserId(userId,numBoardRequested), BoardResponseDTO::new);
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }
}
