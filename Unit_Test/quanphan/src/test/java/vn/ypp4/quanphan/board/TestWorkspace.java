package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.WorkspaceController;
import vn.ypp4.quanphan.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceSettingValueResponseDTO;
import vn.ypp4.quanphan.dto.workspace.WorkspaceUpdateDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class TestWorkspace {
    @Autowired
    private WorkspaceController workspaceController;
    @Test
    void getWorkspaceWhereUserIsMember(){
        int userId=1;


        List<WorkspaceResponseDTO> result=workspaceController.getWorkspaceUserIsMember(userId);

        assertEquals(3,result.size());
    }
    @Test
    void getWorkspaceById() {
        int workspaceId = 1;


        WorkspaceResponseDTO result = workspaceController.getWorkspaceById(workspaceId);

        assertNotNull(result);
    }
    @Test
    void updateWorkspace_Success(){
        //Arrange
        int workspaceId = 1;
        WorkspaceUpdateDTO updateWorkspace = new WorkspaceUpdateDTO(1,"Updated Workspace A", "Updated description for Workspace A", LocalDateTime.now(),1);
        //Act
        int result = workspaceController.updateWorkspace(updateWorkspace);

        //Assert
        assertEquals(1, result);
    }
    @Test
    void getSettingValueByKeyNameAndWorkspaceId(){
        //Arrange
        String keyName = "visibility";
        int workspaceId = 1;

        //Act
        WorkspaceSettingValueResponseDTO result = workspaceController.getSettingValueByKeyNameWorkspaceId(keyName, workspaceId);

        //Assert
        assertNotNull(result);
    }
}
