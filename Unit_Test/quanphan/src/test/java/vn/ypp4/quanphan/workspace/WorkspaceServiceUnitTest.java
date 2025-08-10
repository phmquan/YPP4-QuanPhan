package vn.ypp4.quanphan.workspace;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import vn.ypp4.quanphan.domain.Workspace;
import vn.ypp4.quanphan.service.impl.crud.WorkspaceServiceImpl;
import vn.ypp4.quanphan.service.mapper.WorkspaceRowMapper;

class WorkspaceServiceUnitTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    
    @Mock
    private WorkspaceRowMapper workspaceRowMapper;

    @InjectMocks
    private WorkspaceServiceImpl workspaceServiceImpl;

    private Workspace sampleWorkspace;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleWorkspace = new Workspace(
                1, "Test Workspace", "Description", 13, LocalDateTime.now(), 100, LocalDateTime.now(), 0, "logo.png");
    }

    @Test
    void testCreateWorkspace_Success() {
        when(jdbcTemplate.update(anyString(), any(), any(), anyInt(), any(), anyInt(), any(), anyInt(), any())).thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), eq(workspaceRowMapper)))
                .thenReturn(sampleWorkspace);

        Workspace result = workspaceServiceImpl.createWorkspace(
                "Test Workspace", "Description", 13, sampleWorkspace.getCreatedAt(),
                100, null, 0, "logo.png");
        assertNotNull(result);
        assertEquals("Test Workspace", result.getWorkspaceName());
    }

    @Test
    void testCreateWorkspace_NullName() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceServiceImpl.createWorkspace(null, "desc",
                        13, LocalDateTime.now(), 1, null, 0, "logo.png"));
        assertTrue(ex.getMessage().contains("Name cannot be null"));
    }

    @Test
    void testCreateWorkspace_ZeroCategoryId() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceServiceImpl.createWorkspace("name", "desc", 0, LocalDateTime.now(), 1, null, 0, "logo.png"));
        assertTrue(ex.getMessage().contains("Type cannot be null"));
    }

    @Test
    void testCreateWorkspace_ZeroCreatedBy() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceServiceImpl.createWorkspace("name", "desc", 13, LocalDateTime.now(), 0, null, 0, "logo.png"));
        assertTrue(ex.getMessage().contains("CreatedBy cannot be null"));
    }

    @Test
    void testCreateWorkspace_NullCreatedAt() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> workspaceServiceImpl.createWorkspace("name", "desc", 13, null, 1, null, 0, "logo.png"));
        assertTrue(ex.getMessage().contains("CreatedAt cannot be null"));
    }

    @Test
    void testGetWorkspaceById_Found() {
        when(jdbcTemplate.queryForObject(anyString(), eq(workspaceRowMapper), eq(1)))
                .thenReturn(sampleWorkspace);
        Workspace result = workspaceServiceImpl.getWorkspaceById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetWorkspaceById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), eq(workspaceRowMapper), eq(2)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(EmptyResultDataAccessException.class, () -> workspaceServiceImpl.getWorkspaceById(2));
    }

    @Test
    void testGetWorkspaceByName_Found() {
        when(jdbcTemplate.queryForObject(anyString(), eq(workspaceRowMapper),
                eq("Test Workspace")))
                .thenReturn(sampleWorkspace);
        Workspace result = workspaceServiceImpl.getWorkspaceByName("Test Workspace");
        assertNotNull(result);
        assertEquals("Test Workspace", result.getWorkspaceName());
    }

    @Test
    void testGetWorkspaceByName_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), eq(workspaceRowMapper), eq("Unknown")))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(EmptyResultDataAccessException.class, () -> workspaceServiceImpl.getWorkspaceByName("Unknown"));
    }

    @Test
    void testGetAllWorkspaces() {
        List<Workspace> workspaces = Arrays.asList(sampleWorkspace);
        when(jdbcTemplate.query(anyString(), eq(workspaceRowMapper))).thenReturn(workspaces);
        List<Workspace> result = workspaceServiceImpl.getAllWorkspaces();
        assertEquals(1, result.size());
        assertEquals("Test Workspace", result.get(0).getWorkspaceName());
    }

    @Test
    void testUpdateWorkspaceById() {
        when(jdbcTemplate.queryForObject(anyString(), eq(workspaceRowMapper), eq(1)))
                .thenReturn(sampleWorkspace);
        when(jdbcTemplate.update(anyString(), any(), any(), anyInt(), any(), anyInt(), any(), anyInt())).thenReturn(1);

        int rows = workspaceServiceImpl.updateWorkspaceById(
                1,
                "updated",
                "updated bio",
                15,
                LocalDateTime.now(),
                101,
                "updated.png");
        assertEquals(1, rows);
    }

    @Test
    void testDeleteWorkspace() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);
        int rows = workspaceServiceImpl.deleteWorkspace(1);
        assertEquals(1, rows);
    }
}