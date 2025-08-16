package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.BoardRepository;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.WorkspaceRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    @Override
    public int createBoard(BoardCreateDTO createBoard) {
        if(workspaceRepository.existsById(createBoard.getWorkspaceId())){
            return boardRepository.createBoard(createBoard);
        }
        else{
            throw new NullPointerException("Workspace invalid");
        }
    }
    @Override
    public BoardResponseDTO getBoardById(int boardId){
        return new BoardResponseDTO(boardRepository.findBoardById(boardId));
    }
    @Override
    public List<BoardResponseDTO> getMemberBoardByUserId(int userId){
        return boardRepository.findBoardsByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BoardResponseDTO::new)
                .toList();
    }
    @Override
    public List<BoardResponseDTO> getMemberBoardByUserIdAndWorkspaceId(int userId, int workspaceId) {
        if (workspaceRepository.existsById(workspaceId)&& userRepository.existsById(userId)) {
            return boardRepository.findBoardsByUserIdAndWorkspaceId(userId, workspaceId)
                    .stream()
                    .filter(Objects::nonNull)
                    .map(BoardResponseDTO::new)
                    .toList();
        } else {
            throw new NullPointerException("Workspace invalid");
        }
    }
}

