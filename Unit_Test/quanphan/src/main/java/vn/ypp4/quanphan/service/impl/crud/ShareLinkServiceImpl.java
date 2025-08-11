package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.ShareLink;
import vn.ypp4.quanphan.service.mapper.ShareLinkRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShareLinkServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ShareLinkRowMapper shareLinkRowMapper;

    public List<ShareLink> findAll() {
        String sql = "SELECT Id, OwnerTypeId, RolePermissionId, OwnerId, ShareLinkToken, ShareLinkStatus FROM ShareLink";
        return jdbcTemplate.query(sql, shareLinkRowMapper);
    }

    public Optional<ShareLink> findById(int id) {
        String sql = "SELECT Id, OwnerTypeId, RolePermissionId, OwnerId, ShareLinkToken, ShareLinkStatus FROM ShareLink WHERE Id = ?";
        List<ShareLink> shareLinks = jdbcTemplate.query(sql, shareLinkRowMapper, id);
        return shareLinks.isEmpty() ? Optional.empty() : Optional.of(shareLinks.get(0));
    }

    public ShareLink save(ShareLink shareLink) {
        if (shareLink.getId() == 0) {
            return create(shareLink);
        } else {
            return update(shareLink);
        }
    }

    private ShareLink create(ShareLink shareLink) {
        String sql = "INSERT INTO ShareLink (OwnerTypeId, RolePermissionId, OwnerId, ShareLinkToken, ShareLinkStatus) VALUES (?, ?, ?, ?, ?)";

        // Generate unique token if not provided
        if (shareLink.getShareLinkToken() == null || shareLink.getShareLinkToken().isEmpty()) {
            shareLink.setShareLinkToken(UUID.randomUUID().toString());
        }

        jdbcTemplate.update(sql,
                shareLink.getOwnerTypeId(),
                shareLink.getRolePermissionId(),
                shareLink.getOwnerId(),
                shareLink.getShareLinkToken(),
                shareLink.isShareLinkStatus());

        return shareLink;
    }

    private ShareLink update(ShareLink shareLink) {
        String sql = "UPDATE ShareLink SET OwnerTypeId = ?, RolePermissionId = ?, OwnerId = ?, ShareLinkToken = ?, ShareLinkStatus = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                shareLink.getOwnerTypeId(),
                shareLink.getRolePermissionId(),
                shareLink.getOwnerId(),
                shareLink.getShareLinkToken(),
                shareLink.isShareLinkStatus(),
                shareLink.getId());

        return shareLink;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM ShareLink WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<ShareLink> findByShareLinkToken(String shareLinkToken) {
        String sql = "SELECT Id, OwnerTypeId, RolePermissionId, OwnerId, ShareLinkToken, ShareLinkStatus FROM ShareLink WHERE ShareLinkToken = ? AND ShareLinkStatus = true";
        List<ShareLink> shareLinks = jdbcTemplate.query(sql, shareLinkRowMapper, shareLinkToken);
        return shareLinks.isEmpty() ? Optional.empty() : Optional.of(shareLinks.get(0));
    }

    public List<ShareLink> findByOwnerId(int ownerId) {
        String sql = "SELECT Id, OwnerTypeId, RolePermissionId, OwnerId, ShareLinkToken, ShareLinkStatus FROM ShareLink WHERE OwnerId = ?";
        return jdbcTemplate.query(sql, shareLinkRowMapper, ownerId);
    }

    public List<ShareLink> findByOwnerTypeIdAndOwnerId(int ownerTypeId, int ownerId) {
        String sql = "SELECT Id, OwnerTypeId, RolePermissionId, OwnerId, ShareLinkToken, ShareLinkStatus FROM ShareLink WHERE OwnerTypeId = ? AND OwnerId = ?";
        return jdbcTemplate.query(sql, shareLinkRowMapper, ownerTypeId, ownerId);
    }
}
