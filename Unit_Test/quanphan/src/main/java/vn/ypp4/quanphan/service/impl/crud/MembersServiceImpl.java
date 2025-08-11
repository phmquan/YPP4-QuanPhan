package vn.ypp4.quanphan.service.impl.crud;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.ypp4.quanphan.domain.Members;
import vn.ypp4.quanphan.service.mapper.MembersRowMapper;

@Service
@RequiredArgsConstructor
public class MembersServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final MembersRowMapper membersRowMapper;

    @Transactional
    public Members addMember(int userId, int rolePermissionId, int ownerTypeId, int ownerId,
            int invitedBy, LocalDateTime joinedAt, String memberStatus) {

        if (joinedAt == null) {
            throw new IllegalArgumentException("JoinedAt cannot be null");
        }
        if (memberStatus == null || memberStatus.isBlank()) {
            throw new IllegalArgumentException("Member status cannot be null or empty");
        }

        // Check if user exists
        Integer userExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM User WHERE Id = ?",
                Integer.class,
                userId);

        if (userExists == null || userExists == 0) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        // Check if role permission exists
        Integer roleExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM RolePermission WHERE Id = ?",
                Integer.class,
                rolePermissionId);

        if (roleExists == null || roleExists == 0) {
            throw new IllegalArgumentException("Role permission with ID " + rolePermissionId + " not found");
        }

        // Check if owner type exists
        Integer ownerTypeExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM OwnerType WHERE Id = ?",
                Integer.class,
                ownerTypeId);

        if (ownerTypeExists == null || ownerTypeExists == 0) {
            throw new IllegalArgumentException("Owner type with ID " + ownerTypeId + " not found");
        }

        // Check if inviter exists
        Integer inviterExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM User WHERE Id = ?",
                Integer.class,
                invitedBy);

        if (inviterExists == null || inviterExists == 0) {
            throw new IllegalArgumentException("Inviting user with ID " + invitedBy + " not found");
        }

        // Check if user is already a member
        // Members existingMember = getMemberByUserAndOwner(userId, ownerTypeId,
        // ownerId);
        // if (existingMember != null) {
        // throw new IllegalStateException("User " + userId + " is already a member of
        // this resource");
        // }

        jdbcTemplate.update(
                "INSERT INTO Members (UserId, RolePermissionId, OwnerTypeId, OwnerId, InvitedBy, JoinedAt, MemberStatus) "
                        +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                userId, rolePermissionId, ownerTypeId, ownerId, invitedBy, joinedAt, memberStatus);

        return jdbcTemplate.queryForObject(
                "SELECT * FROM Members WHERE Id = LAST_INSERT_ID()",
                membersRowMapper);
    }

    public Members getMemberById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Members WHERE Id = ?",
                    membersRowMapper,
                    id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Members> getMemberByUserAndOwnerType(int userId, int ownerTypeId) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Members WHERE UserId = ? AND OwnerTypeId = ?",
                    membersRowMapper,
                    userId, ownerTypeId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Members> getMemberByUserAndOwner(int userId, int ownerTypeId, int ownerId) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Members WHERE UserId = ? AND OwnerTypeId = ? AND OwnerId = ?",
                    membersRowMapper,
                    userId, ownerTypeId, ownerId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Members> getMembersByOwner(int ownerTypeId, int ownerId) {
        return jdbcTemplate.query(
                "SELECT m.* FROM Members m " +
                        "INNER JOIN User u ON m.UserId = u.Id " +
                        "WHERE m.OwnerTypeId = ? AND m.OwnerId = ? " +
                        "ORDER BY u.Username",
                membersRowMapper,
                ownerTypeId, ownerId);
    }

    public List<Members> getMembersByUser(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM Members WHERE UserId = ? ORDER BY JoinedAt DESC",
                membersRowMapper,
                userId);
    }

    public List<Members> getMembersByStatus(String memberStatus) {
        if (memberStatus == null || memberStatus.isBlank()) {
            throw new IllegalArgumentException("Member status cannot be null or empty");
        }

        return jdbcTemplate.query(
                "SELECT m.* FROM Members m " +
                        "INNER JOIN User u ON m.UserId = u.Id " +
                        "WHERE m.MemberStatus = ? " +
                        "ORDER BY u.Username",
                membersRowMapper,
                memberStatus);
    }

    @Transactional
    public int updateMemberRole(int id, int rolePermissionId) {
        // Check if member exists
        Members member = getMemberById(id);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + id + " not found");
        }

        // Check if role permission exists
        Integer roleExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM RolePermission WHERE Id = ?",
                Integer.class,
                rolePermissionId);

        if (roleExists == null || roleExists == 0) {
            throw new IllegalArgumentException("Role permission with ID " + rolePermissionId + " not found");
        }

        return jdbcTemplate.update(
                "UPDATE Members SET RolePermissionId = ? WHERE Id = ?",
                rolePermissionId, id);
    }

    @Transactional
    public int updateMemberStatus(int id, String memberStatus) {
        if (memberStatus == null || memberStatus.isBlank()) {
            throw new IllegalArgumentException("Member status cannot be null or empty");
        }

        // Check if member exists
        Members member = getMemberById(id);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + id + " not found");
        }

        return jdbcTemplate.update(
                "UPDATE Members SET MemberStatus = ? WHERE Id = ?",
                memberStatus, id);
    }

    @Transactional
    public int removeMember(int id) {
        // Check if member exists
        Members member = getMemberById(id);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + id + " not found");
        }

        return jdbcTemplate.update(
                "DELETE FROM Members WHERE Id = ?",
                id);
    }

    public boolean isUserMember(int userId, int ownerTypeId, int ownerId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Members " +
                        "WHERE UserId = ? AND OwnerTypeId = ? AND OwnerId = ? AND MemberStatus = 'ACTIVE'",
                Integer.class,
                userId, ownerTypeId, ownerId);

        return count != null && count > 0;
    }

    public boolean isUserAdmin(int userId, int ownerTypeId, int ownerId) {
        // Assuming role ID 1 is for admin (you may need to adjust this based on your
        // role IDs)
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Members " +
                        "WHERE UserId = ? AND OwnerTypeId = ? AND OwnerId = ? " +
                        "AND RolePermissionId = 1 AND MemberStatus = 'ACTIVE'",
                Integer.class,
                userId, ownerTypeId, ownerId);

        return count != null && count > 0;
    }

    public int countMembers(int ownerTypeId, int ownerId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Members " +
                        "WHERE OwnerTypeId = ? AND OwnerId = ? AND MemberStatus = 'ACTIVE'",
                Integer.class,
                ownerTypeId, ownerId);

        return count != null ? count : 0;
    }
}
