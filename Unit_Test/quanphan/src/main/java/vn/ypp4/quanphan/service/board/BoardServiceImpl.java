package vn.ypp4.quanphan.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.board.BoardCreateDTO;
import vn.ypp4.quanphan.domain.dto.board.BoardResponseDTO;
import vn.ypp4.quanphan.repository.BoardRepository;
import vn.ypp4.quanphan.repository.UserRepository;
import vn.ypp4.quanphan.repository.WorkspaceRepository;
import vn.ypp4.quanphan.service.dto.EntityToDtoMapper;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private UserRepository userRepository;
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
        return EntityToDtoMapper.mapToDto(boardRepository.findBoardsByUserId(userId), BoardResponseDTO::new);
    }
    @Override
    public List<BoardResponseDTO> getMemberBoardByUserIdAndWorkspaceId(int userId, int workspaceId) {
        if (workspaceRepository.existsById(workspaceId)&& userRepository.existsById(userId)) {
            return EntityToDtoMapper.mapToDto(boardRepository.findBoardsByUserIdAndWorkspaceId(userId, workspaceId), BoardResponseDTO::new);
        } else {
            throw new NullPointerException("Workspace invalid");
        }
    }
}
