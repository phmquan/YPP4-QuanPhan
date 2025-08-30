package vn.ypp4.quanphan.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.ypp4.quanphan.api.controller.WorkspaceController;
import vn.ypp4.quanphan.api.dto.member.MemberWorkspaceResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceSettingValueResponseDTO;
import vn.ypp4.quanphan.api.dto.workspace.WorkspaceUpdateDTO;
import vn.ypp4.quanphan.api.service.workspace.WorkspaceMemberService;
import vn.ypp4.quanphan.api.service.workspace.WorkspaceService;
import vn.ypp4.quanphan.api.service.workspace.WorkspaceSettingService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestWorkspace {

    @Mock
    private WorkspaceService workspaceService;

    @Mock
    private WorkspaceSettingService workspaceSettingService;

    @Mock
    private WorkspaceMemberService workspaceMemberService;

    @InjectMocks
    private WorkspaceController workspaceController;

    private WorkspaceResponseDTO sampleWorkspace;
    private List<WorkspaceResponseDTO> sampleWorkspaces;
    private WorkspaceUpdateDTO updateWorkspace;
    private WorkspaceSettingValueResponseDTO sampleSetting;
    private MemberWorkspaceResponseDTO sampleMembers;

    @BeforeEach
    void setUp() {
        sampleWorkspace = new WorkspaceResponseDTO();
        // Set sample data - adjust according to your DTO structure

        sampleWorkspaces = Arrays.asList(sampleWorkspace, sampleWorkspace, sampleWorkspace);

        updateWorkspace = new WorkspaceUpdateDTO(1, "Updated Workspace A", "Updated description for Workspace A",
                LocalDateTime.now(), 1);

        sampleSetting = new WorkspaceSettingValueResponseDTO();
        sampleMembers = new MemberWorkspaceResponseDTO();
    }

    @Test
    void getWorkspaceUserIsMember_Success() {
        // Arrange
        int userId = 1;
        ResponseEntity<List<WorkspaceResponseDTO>> expectedResponse = ResponseEntity.ok(sampleWorkspaces);
        when(workspaceService.getMemberWorkspacesByUserId(userId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<WorkspaceResponseDTO>> result = workspaceController.getWorkspaceUserIsMember(userId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(3, result.getBody().size());
        verify(workspaceService, times(1)).getMemberWorkspacesByUserId(userId);
    }

    @Test
    void getWorkspaceById_Success() {
        // Arrange
        int workspaceId = 1;
        ResponseEntity<WorkspaceResponseDTO> expectedResponse = ResponseEntity.ok(sampleWorkspace);
        when(workspaceService.getWorkspaceById(workspaceId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<WorkspaceResponseDTO> result = workspaceController.getWorkspaceById(workspaceId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(workspaceService, times(1)).getWorkspaceById(workspaceId);
    }

    @Test
    void getWorkspaceById_NotFound() {
        // Arrange
        int workspaceId = 999;
        ResponseEntity<WorkspaceResponseDTO> expectedResponse = ResponseEntity.notFound().build();
        when(workspaceService.getWorkspaceById(workspaceId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<WorkspaceResponseDTO> result = workspaceController.getWorkspaceById(workspaceId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(workspaceService, times(1)).getWorkspaceById(workspaceId);
    }

    @Test
    void updateWorkspace_Success() {
        // Arrange
        ResponseEntity<Integer> expectedResponse = ResponseEntity.ok(1);
        when(workspaceService.updateWorkspace(updateWorkspace)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<Integer> result = workspaceController.updateWorkspace(updateWorkspace);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody());
        verify(workspaceService, times(1)).updateWorkspace(updateWorkspace);
    }

    @Test
    void updateWorkspace_BadRequest_InvalidId() {
        // Arrange
        WorkspaceUpdateDTO invalidWorkspace = new WorkspaceUpdateDTO(0, "Invalid", "Invalid", LocalDateTime.now(), 1);

        // Act
        ResponseEntity<Integer> result = workspaceController.updateWorkspace(invalidWorkspace);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(workspaceService, never()).updateWorkspace(any());
    }

    @Test
    void getSettingValueByKeyNameAndWorkspaceId_Success() {
        // Arrange
        String keyName = "visibility";
        int workspaceId = 1;
        ResponseEntity<WorkspaceSettingValueResponseDTO> expectedResponse = ResponseEntity.ok(sampleSetting);
        when(workspaceSettingService.getSettingValueByKeyNameAndWorkspaceId(keyName, workspaceId))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<WorkspaceSettingValueResponseDTO> result = workspaceController
                .getSettingValueByKeyNameWorkspaceId(keyName, workspaceId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(workspaceSettingService, times(1)).getSettingValueByKeyNameAndWorkspaceId(keyName, workspaceId);
    }

    @Test
    void getMembersByWorkspace_Success() {
        // Arrange
        int workspaceId = 1;
        ResponseEntity<MemberWorkspaceResponseDTO> expectedResponse = ResponseEntity.ok(sampleMembers);
        when(workspaceMemberService.getWorkspaceMembersByUserId(workspaceId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<MemberWorkspaceResponseDTO> result = workspaceController.getMembersByWorkspace(workspaceId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(workspaceMemberService, times(1)).getWorkspaceMembersByUserId(workspaceId);
    }

    @Test
    void getWorkspaceUserIsMember_InternalServerError() {
        // Arrange
        int userId = 1;
        ResponseEntity<List<WorkspaceResponseDTO>> expectedResponse = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        when(workspaceService.getMemberWorkspacesByUserId(userId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<WorkspaceResponseDTO>> result = workspaceController.getWorkspaceUserIsMember(userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(workspaceService, times(1)).getMemberWorkspacesByUserId(userId);
    }
}
