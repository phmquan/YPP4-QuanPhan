package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.SettingKeySettingOption;
import vn.ypp4.quanphan.service.mapper.SettingKeySettingOptionRowMapper;

import java.util.List;
import java.util.Optional;

@Service
public class SettingKeySettingOptionServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SettingKeySettingOptionRowMapper settingKeySettingOptionRowMapper;

    public List<SettingKeySettingOption> findAll() {
        String sql = "SELECT SettingKeyId, SettingOptionId FROM SettingKeySettingOption";
        return jdbcTemplate.query(sql, settingKeySettingOptionRowMapper);
    }

    public Optional<SettingKeySettingOption> findBySettingKeyIdAndSettingOptionId(int settingKeyId,
            int settingOptionId) {
        String sql = "SELECT SettingKeyId, SettingOptionId FROM SettingKeySettingOption WHERE SettingKeyId = ? AND SettingOptionId = ?";
        List<SettingKeySettingOption> settingKeySettingOptions = jdbcTemplate.query(sql,
                settingKeySettingOptionRowMapper, settingKeyId, settingOptionId);
        return settingKeySettingOptions.isEmpty() ? Optional.empty() : Optional.of(settingKeySettingOptions.get(0));
    }

    public SettingKeySettingOption save(SettingKeySettingOption settingKeySettingOption) {
        String sql = "INSERT INTO SettingKeySettingOption (SettingKeyId, SettingOptionId) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                settingKeySettingOption.getSettingKeyId(),
                settingKeySettingOption.getSettingOptionId());

        return settingKeySettingOption;
    }

    public void deleteBySettingKeyIdAndSettingOptionId(int settingKeyId, int settingOptionId) {
        String sql = "DELETE FROM SettingKeySettingOption WHERE SettingKeyId = ? AND SettingOptionId = ?";
        jdbcTemplate.update(sql, settingKeyId, settingOptionId);
    }

    public List<SettingKeySettingOption> findBySettingKeyId(int settingKeyId) {
        String sql = "SELECT SettingKeyId, SettingOptionId FROM SettingKeySettingOption WHERE SettingKeyId = ?";
        return jdbcTemplate.query(sql, settingKeySettingOptionRowMapper, settingKeyId);
    }

    public List<SettingKeySettingOption> findBySettingOptionId(int settingOptionId) {
        String sql = "SELECT SettingKeyId, SettingOptionId FROM SettingKeySettingOption WHERE SettingOptionId = ?";
        return jdbcTemplate.query(sql, settingKeySettingOptionRowMapper, settingOptionId);
    }
}
