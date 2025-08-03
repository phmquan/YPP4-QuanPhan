package vn.ypp4.quanphan.WorkspaceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.service.WorkspaceService;
import vn.ypp4.quanphan.util.constant.WorkspaceTypeEnum;

class WorkspaceServiceUnitTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WorkspaceService workspaceService;

    private Workspace sampleWorkspace;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleWorkspace = new Workspace(
                1, "Test Workspace", "Description", WorkspaceTypeEnum.HUMAN_RESOURCES, Instant.now(), 100);
    }

    @Test
    void testCreateWorkspace_Success() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), anyInt())).thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq("Test Workspace")))
                .thenReturn(sampleWorkspace);

        Workspace result = workspaceService.createWorkspace(
                "Test Workspace", "Description", WorkspaceTypeEnum.HUMAN_RESOURCES, sampleWorkspace.getCreatedAt(),
                100);
        assertNotNull(result);
        assertEquals("Test Workspace", result.getName());
    }

    @Test
    void testCreateWorkspace_NullName() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> workspaceService.createWorkspace(null, "desc",
                WorkspaceTypeEnum.HUMAN_RESOURCES, Instant.now(), 1));
        assertTrue(ex.getMessage().contains("Name cannot be null"));
    }

    @Test
    void testCreateWorkspace_NullType() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceService.createWorkspace("name", "desc", null, Instant.now(), 1));
        assertTrue(ex.getMessage().contains("Type cannot be null"));
    }

    @Test
    void testCreateWorkspace_NullCreatedBy() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceService.createWorkspace("name", "desc", WorkspaceTypeEnum.HUMAN_RESOURCES, Instant.now(),
                        0));
        assertTrue(ex.getMessage().contains("CreatedBy cannot be null"));
    }

    @Test
    void testCreateWorkspace_NullCreatedAt() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceService.createWorkspace("name", "desc", WorkspaceTypeEnum.HUMAN_RESOURCES, null,
                        0));
        assertTrue(ex.getMessage().contains("CreatedAt cannot be null"));
    }

    @Test
    void testGetWorkspaceById_Found() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(sampleWorkspace);
        Workspace result = workspaceService.getWorkspaceById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetWorkspaceById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(2)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(EmptyResultDataAccessException.class, () -> workspaceService.getWorkspaceById(2));
    }

    @Test
    void testGetWorkspaceByName_Found() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq("Test Workspace")))
                .thenReturn(sampleWorkspace);
        Workspace result = workspaceService.getWorkspaceByName("Test Workspace");
        assertNotNull(result);
        assertEquals("Test Workspace", result.getName());
    }

    @Test
    void testGetWorkspaceByName_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq("Unknown")))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(EmptyResultDataAccessException.class, () -> workspaceService.getWorkspaceByName("Unknown"));
    }

    @Test
    void testGetAllWorkspaces() {
        List<Workspace> workspaces = Arrays.asList(sampleWorkspace);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(workspaces);
        List<Workspace> result = workspaceService.getAllWorkspaces();
        assertEquals(1, result.size());
        assertEquals("Test Workspace", result.get(0).getName());
    }

    @Test
    void testUpdateWorkspace() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), anyInt())).thenReturn(1);
        int rows = workspaceService.updateWorkspace(1, "Updated", "Updated desc", WorkspaceTypeEnum.HUMAN_RESOURCES);
        assertEquals(1, rows);
    }

    @Test
    void testDeleteWorkspace() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);
        int rows = workspaceService.deleteWorkspace(1);
        assertEquals(1, rows);
    }
}