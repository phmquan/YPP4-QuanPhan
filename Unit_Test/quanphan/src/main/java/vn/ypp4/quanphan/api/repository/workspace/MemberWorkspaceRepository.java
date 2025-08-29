package vn.ypp4.quanphan.api.repository.workspace;

import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;

@Repository
public interface MemberWorkspaceRepository {
    MemberWorkspaceResponseDTO getWorkspaceMembersByUserId(int workspaceId);
}
