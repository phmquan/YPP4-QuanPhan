package vn.ypp4.quanphan.repository.workspace;

import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.dto.member.MemberWorkspaceResponseDTO;

@Repository
public interface MemberWorkspaceRepository {
    MemberWorkspaceResponseDTO getWorkspaceMembersByUserId(int workspaceId);
}
