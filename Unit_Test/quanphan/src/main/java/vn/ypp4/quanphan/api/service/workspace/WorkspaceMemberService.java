package vn.ypp4.quanphan.api.service.workspace;

import org.springframework.http.ResponseEntity;
import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;

public interface WorkspaceMemberService {
    ResponseEntity<MemberWorkspaceResponseDTO> getWorkspaceMembersByUserId(int workspaceId);
}
