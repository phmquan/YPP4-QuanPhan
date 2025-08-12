package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.dto.BoardResponseDTO;
import vn.ypp4.quanphan.repository.interf.UserFavoritedBoardRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserFavoritedBoardService {
    private final UserFavoritedBoardRepository userFavoritedBoardRepository;
    public List<BoardResponseDTO> getFavoritedBoardsByUserId(int userId){
        return userFavoritedBoardRepository.getFavoritedBoardsByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BoardResponseDTO::new)
                .toList();
    }
}
