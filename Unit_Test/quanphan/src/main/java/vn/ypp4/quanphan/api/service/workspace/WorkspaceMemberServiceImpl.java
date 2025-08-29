package vn.ypp4.quanphan.api.service.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;
import vn.ypp4.quanphan.api.repository.workspace.MemberWorkspaceRepository;
import vn.ypp4.quanphan.api.repository.workspace.WorkspaceRepository;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {
    private final WorkspaceRepository workspaceRepository;
    private final MemberWorkspaceRepository memberWorkspaceRepository;
    @Override
    public MemberWorkspaceResponseDTO getWorkspaceMembersByUserId(int workspaceId) {
        if(workspaceRepository.existsById(workspaceId)) {
            return memberWorkspaceRepository.getWorkspaceMembersByUserId(workspaceId);
        } else {
            throw new RuntimeException("Workspace not found");
        }
    }
}
