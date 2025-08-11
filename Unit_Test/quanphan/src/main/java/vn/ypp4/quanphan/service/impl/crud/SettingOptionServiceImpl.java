package vn.ypp4.quanphan.service.impl.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.ypp4.quanphan.domain.SettingOption;
import vn.ypp4.quanphan.service.mapper.SettingOptionRowMapper;

import java.util.List;
import java.util.Optional;

@Service
public class SettingOptionServiceImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SettingOptionRowMapper settingOptionRowMapper;

    public List<SettingOption> findAll() {
        String sql = "SELECT Id, DisplayValue, SettingOptionValue FROM SettingOption";
        return jdbcTemplate.query(sql, settingOptionRowMapper);
    }

    public Optional<SettingOption> findById(int id) {
        String sql = "SELECT Id, DisplayValue, SettingOptionValue FROM SettingOption WHERE Id = ?";
        List<SettingOption> settingOptions = jdbcTemplate.query(sql, settingOptionRowMapper, id);
        return settingOptions.isEmpty() ? Optional.empty() : Optional.of(settingOptions.get(0));
    }

    public SettingOption save(SettingOption settingOption) {
        if (settingOption.getId() == 0) {
            return create(settingOption);
        } else {
            return update(settingOption);
        }
    }

    private SettingOption create(SettingOption settingOption) {
        String sql = "INSERT INTO SettingOption (DisplayValue, SettingOptionValue) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                settingOption.getDisplayValue(),
                settingOption.getSettingOptionValue());

        return settingOption;
    }

    private SettingOption update(SettingOption settingOption) {
        String sql = "UPDATE SettingOption SET DisplayValue = ?, SettingOptionValue = ? WHERE Id = ?";

        jdbcTemplate.update(sql,
                settingOption.getDisplayValue(),
                settingOption.getSettingOptionValue(),
                settingOption.getId());

        return settingOption;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM SettingOption WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<SettingOption> findBySettingOptionValue(String settingOptionValue) {
        String sql = "SELECT Id, DisplayValue, SettingOptionValue FROM SettingOption WHERE SettingOptionValue = ?";
        List<SettingOption> settingOptions = jdbcTemplate.query(sql, settingOptionRowMapper, settingOptionValue);
        return settingOptions.isEmpty() ? Optional.empty() : Optional.of(settingOptions.get(0));
    }
}
