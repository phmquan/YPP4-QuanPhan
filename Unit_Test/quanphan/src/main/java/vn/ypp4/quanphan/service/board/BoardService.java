package vn.ypp4.quanphan.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.BoardRepository;
import vn.ypp4.quanphan.repository.WorkspaceRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;
    public int createBoard(BoardCreateDTO createBoard) {
        if(workspaceRepository.existById(createBoard.getWorkspaceId())){
            return boardRepository.createBoard(createBoard);
        }
        else{
            throw new NullPointerException("Workspace invalid");
        }

    }
    public BoardResponseDTO getBoardById(int boardId){
        return new BoardResponseDTO(boardRepository.getBoardById(boardId));
    }
    public List<BoardResponseDTO> getMemberBoardByUserId(int userId){
        return boardRepository.getMemberBoardsByUserId(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(BoardResponseDTO::new)
                .toList();
    }


}
