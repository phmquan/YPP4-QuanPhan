package vn.ypp4.quanphan.api.service.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberWorkspaceResponseDTO> getWorkspaceMembersByUserId(int workspaceId) {
        try {
            if (workspaceRepository.existsById(workspaceId)) {
                MemberWorkspaceResponseDTO members = memberWorkspaceRepository.getWorkspaceMembersByUserId(workspaceId);
                return members!=null? ResponseEntity.ok(members) :
                        ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
