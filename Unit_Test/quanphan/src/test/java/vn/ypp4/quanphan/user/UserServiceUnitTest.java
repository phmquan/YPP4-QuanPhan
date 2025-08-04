package vn.ypp4.quanphan.user;

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

import vn.ypp4.quanphan.domain.User;
import vn.ypp4.quanphan.service.impl.UserServiceImpl;

public class UserServiceUnitTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User(
                1, "testuser", "bio", "test@email.com",
                Instant.now(), Instant.now(), "pic.png");
    }

    @Test
    void testCreateUser_Success() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any())).thenReturn(1);
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq("test@email.com")))
                .thenReturn(sampleUser);

        User result = userServiceImpl.createUser(
                "testuser", "bio", "test@email.com",
                sampleUser.getLastActive(), sampleUser.getCreatedAt(), "pic.png");
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testCreateUser_NullUsername() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> userServiceImpl.createUser(null, "bio", "test@email.com", Instant.now(), Instant.now(),
                        "pic.png"));
        assertTrue(ex.getMessage().contains("Username cannot be null"));
    }

    @Test
    void testCreateUser_NullEmail() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> userServiceImpl.createUser("testuser", "bio", null, Instant.now(), Instant.now(), "pic.png"));
        assertTrue(ex.getMessage().contains("Email cannot be null"));
    }

    @Test
    void testCreateUser_NullCreatedAt() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> userServiceImpl.createUser("testuser", "bio", "test@email.com", Instant.now(), null, "pic.png"));
        assertTrue(ex.getMessage().contains("CreatedAt cannot be null"));
    }

    @Test
    void testGetUserByEmail_Found() {
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq("test@email.com")))
                .thenReturn(sampleUser);
        User result = userServiceImpl.getUserByEmail("test@email.com");
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetUserByEmail_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq("unknown@email.com")))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(EmptyResultDataAccessException.class, () -> userServiceImpl.getUserByEmail("unknown@email.com"));
    }

    @Test
    void testGetAllUser() {
        List<User> users = Arrays.asList(sampleUser);
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<User>>any())).thenReturn(users);
        List<User> result = userServiceImpl.getAllUser();
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void testDeleteUserById() {
        when(jdbcTemplate.update(anyString(), any(RowMapper.class), eq(1))).thenReturn(1);
        int rows = userServiceImpl.DeleteUserById(1);
        assertEquals(1, rows);
    }

    @Test
    void testGetUserById_Found() {
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(1)))
                .thenReturn(sampleUser);
        User result = userServiceImpl.getUserById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(2)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(EmptyResultDataAccessException.class, () -> userServiceImpl.getUserById(2));
    }

    @Test
    void testUpdateUserById() {
        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper<User>>any(), eq(1)))
                .thenReturn(sampleUser);
        when(jdbcTemplate.update(anyString(), any(RowMapper.class), any(), any(), any(), anyInt())).thenReturn(1);
        int rows = userServiceImpl.UpdateUserById(1, "updated", "updated bio", "updated.png");
        assertEquals(1, rows);
    }
}