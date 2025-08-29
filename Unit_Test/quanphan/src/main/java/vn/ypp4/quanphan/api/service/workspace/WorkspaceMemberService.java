package vn.ypp4.quanphan.api.service.workspace;

import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;

public interface WorkspaceMemberService {
    MemberWorkspaceResponseDTO getWorkspaceMembersByUserId(int workspaceId);
}
