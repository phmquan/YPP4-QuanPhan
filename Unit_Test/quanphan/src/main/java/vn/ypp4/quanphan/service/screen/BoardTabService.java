package vn.ypp4.quanphan.service.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import vn.ypp4.quanphan.domain.Board;
import vn.ypp4.quanphan.domain.Members;
import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.dto.BoardDTO;
import vn.ypp4.quanphan.dto.BoardTabScreenDTO;
import vn.ypp4.quanphan.dto.UserSectionDTO;
import vn.ypp4.quanphan.dto.UserStarredBoardSectionDTO;
import vn.ypp4.quanphan.dto.UserViewHistorySectionDTO;
import vn.ypp4.quanphan.dto.WorkspaceDTO;
import vn.ypp4.quanphan.dto.WorkspaceSectionDTO;
import vn.ypp4.quanphan.service.impl.crud.BoardServiceImpl;
import vn.ypp4.quanphan.service.impl.crud.MembersServiceImpl;
import vn.ypp4.quanphan.service.impl.crud.UserServiceImpl;
import vn.ypp4.quanphan.service.impl.crud.UserStarredBoardServiceImpl;
import vn.ypp4.quanphan.service.impl.crud.UserViewHistoryServiceImpl;
import vn.ypp4.quanphan.service.impl.crud.WorkspaceServiceImpl;
import vn.ypp4.quanphan.service.mapper.dto.EntityDTOMapper;
import vn.ypp4.quanphan.util.constant.OwnerTypeEnum;

@AllArgsConstructor
public class BoardTabService {
    private final UserStarredBoardServiceImpl userStarredBoardServiceImpl;
    private final UserViewHistoryServiceImpl userViewHistoryServiceImpl;
    private final WorkspaceServiceImpl workspaceServiceImpl;
    private final BoardServiceImpl boardServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final MembersServiceImpl membersServiceImpl;
    private final EntityDTOMapper entityDTOMapper;

    private final int boardOwnerType = OwnerTypeEnum.valueOf("board").ordinal() + 1;
    private final int workspaceOwnerType = OwnerTypeEnum.valueOf("workspace").ordinal() + 1;

    public BoardTabScreenDTO getBoardTabScreen(String userEmail) {
        // User section
        User currentUser = userServiceImpl.getUserByEmail(userEmail);
        UserSectionDTO currentUserDTO = new UserSectionDTO(currentUser.getId(), currentUser.getUsername(),
                currentUser.getPictureUrl());
        // Starred Board section
        List<BoardDTO> starredBoards = userStarredBoardServiceImpl
                .findByUserId(currentUser.getId()).stream()
                .map(starred -> boardServiceImpl.getBoardById(starred.getBoardId()))
                .map(board -> entityDTOMapper.toDTO(board,
                        b -> new BoardDTO(b.getId(), b.getBoardName(), b.getBackgroundUrl())))
                .collect(Collectors.toList());
        UserStarredBoardSectionDTO starredBoardSectionDTO = new UserStarredBoardSectionDTO(starredBoards);
        // Recent section
        List<BoardDTO> historyBoards = userViewHistoryServiceImpl
                .findByUserIdAndOwnerTypeId(currentUser.getId(), boardOwnerType).stream()
                .map(history -> boardServiceImpl.getBoardById(history.getOwnerId()))
                .map(board -> entityDTOMapper.toDTO(board,
                        b -> new BoardDTO(b.getId(), b.getBoardName(), b.getBackgroundUrl())))
                .collect(Collectors.toList());
        UserViewHistorySectionDTO userViewHistorySectionDTO = new UserViewHistorySectionDTO(historyBoards);
        // Workspace section
        // Get Workspace where current user is member and board in workspace where user
        // is also member
        List<Members> userMemberWorkspaces = membersServiceImpl.getMemberByUserAndOwnerType(currentUser.getId(),
                workspaceOwnerType);
        List<Members> userMemberBoards = membersServiceImpl.getMemberByUserAndOwnerType(currentUser.getId(),
                boardOwnerType);
        List<WorkspaceDTO> workspaceDTO = new ArrayList<>();
        for (Members member : userMemberWorkspaces) {
            Workspace workspaceItem = workspaceServiceImpl.getWorkspaceById(member.getOwnerId());
            List<Board> boardInWorkspace = new ArrayList<>();
            for (Members userMemberBoard : userMemberBoards) {
                Board boardItem = boardServiceImpl.getBoardById(userMemberBoard.getOwnerId());
                boardInWorkspace.add(boardItem);
            }
            List<BoardDTO> boardInWorkspaceDTO = new ArrayList<>();
            for (Board boardItem : boardInWorkspace) {
                BoardDTO boardItemDTO = new BoardDTO(boardItem.getId(), boardItem.getBoardName(),
                        boardItem.getBackgroundUrl());
                boardInWorkspaceDTO.add(boardItemDTO);
            }
            WorkspaceDTO workspaceDTOItem = new WorkspaceDTO(workspaceItem.getId(), workspaceItem.getWorkspaceName(),
                    workspaceItem.getLogoUrl(), boardInWorkspaceDTO);
            workspaceDTO.add(workspaceDTOItem);
        }
        WorkspaceSectionDTO workspaceSectionDTO = new WorkspaceSectionDTO(workspaceDTO);
        // return BoardTabScreenDTO
        return new BoardTabScreenDTO(currentUserDTO, starredBoardSectionDTO, userViewHistorySectionDTO,
                workspaceSectionDTO);
    }
}
