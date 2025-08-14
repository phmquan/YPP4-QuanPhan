package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.WorkspaceController;
import vn.ypp4.quanphan.domain.dto.WorkspaceResponseDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TestWorkspace {
    @Autowired
    private WorkspaceController workspaceController;
    @Test
    void getWorkspaceWhereUserIsMember(){
        int userId=1;
        List<WorkspaceResponseDTO> expected=new ArrayList<>();
        expected.add(new WorkspaceResponseDTO(1,"Workspace A","Workspace for project A","logo1.png"));
        expected.add(new WorkspaceResponseDTO(2,"Workspace B","Workspace for project B","logo2.png"));
        expected.add(new WorkspaceResponseDTO(3,"Workspace C","Workspace for project C","logo3.png"));

        List<WorkspaceResponseDTO> result=workspaceController.getWorkspaceUserIsMember(userId);

        assertEquals(expected.size(),result.size());
    }
    @Test
    void getWorkspaceById() {
        int workspaceId = 1;
        WorkspaceResponseDTO expected = new WorkspaceResponseDTO(1, "Workspace A", "Workspace for project A", "logo1.png");

        WorkspaceResponseDTO result = workspaceController.getWorkspaceById(workspaceId);

        assertEquals(expected.getWorkspaceName(), result.getWorkspaceName());
    }
}
