package vn.ypp4.quanphan.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.ypp4.quanphan.domain.dto.SettingValueResponseDTO;


@Repository
@RequiredArgsConstructor
public class SettingValueRepository {
    private final JdbcTemplate jdbcTemplate;

    public SettingValueResponseDTO findByKeyNameAndWorkspaceId(String keyName, int workspaceId) {
        String sql="SELECT \n" +
                "        sv.Id as SettingValueId,\n" +
                "        sv.OwnerId,\n" +
                "        sk.KeyName,\n" +
                "        so.DisplayValue \n" +
                "    FROM SettingValue sv\n" +
                "        JOIN SettingKey sk ON sk.Id = sv.SettingKeyId\n" +
                "        LEFT JOIN SettingOption so ON sv.SettingContent= so.Id \n" +
                "            AND sk.IsBoolean=FALSE\n" +
                "        JOIN OwnerType owt ON owt.Id = sk.OwnerTypeId \n" +
                "            AND owt.OwnerTypeValue = 'workspace'\n" +
                "    WHERE sk.KeyName = ? AND sv.OwnerId=?\n";
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(SettingValueResponseDTO.class),
                keyName, workspaceId);
    }
}
