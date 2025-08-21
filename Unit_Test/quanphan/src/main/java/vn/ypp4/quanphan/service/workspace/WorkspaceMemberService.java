package vn.ypp4.quanphan.service.workspace;

import vn.ypp4.quanphan.dto.member.MemberWorkspaceResponseDTO;

public interface WorkspaceMemberService {
    MemberWorkspaceResponseDTO getWorkspaceMembersByUserId(int workspaceId);
}
