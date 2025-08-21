package vn.ypp4.quanphan.repository.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.dto.workspace.WorkspaceSettingValueResponseDTO;

@Repository
@RequiredArgsConstructor
public class WorkspaceSettingRepositoryImpl implements WorkspaceSettingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public WorkspaceSettingValueResponseDTO findByKeyNameAndOwnerId(String keyName, int workspaceId) {
        String sql=
                """
                SELECT\s
                  sv.Id,\s
                  sv.OwnerId as WorkspaceId,\s
                  sk.KeyName,\s
                  so.DisplayValue\s
                FROM\s
                  SettingValue sv\s
                  JOIN OwnerType ot ON ot.Id = sv.OwnerTypeId\s
                  JOIN SettingKey sk ON sk.Id = sv.SettingKeyId\s
                  JOIN SettingOption so ON so.Id = sv.SettingContent\s
                  AND sk.IsBoolean = false\s
                WHERE\s
                  ot.OwnerTypeValue = 'workspace'\s
                  AND sv.SettingKey = ?\s
                  AND sv.OwnerId = ?
                """;
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(WorkspaceSettingValueResponseDTO.class),
                keyName, workspaceId);
    }
}
