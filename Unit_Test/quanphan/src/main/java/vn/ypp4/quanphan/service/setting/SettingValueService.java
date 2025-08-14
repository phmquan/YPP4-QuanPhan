package vn.ypp4.quanphan.service.setting;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.ypp4.quanphan.domain.dto.SettingValueResponseDTO;
import vn.ypp4.quanphan.repository.SettingValueRepository;

@Service
@RequiredArgsConstructor
public class SettingValueService {
    private final SettingValueRepository settingValueRepository;
    private final JdbcTemplate jdbcTemplate;

    public SettingValueResponseDTO getSettingValueForWorkspaceByKeyNameAndId(String keyName, int workspaceId) {
        return settingValueRepository.findByKeyNameAndWorkspaceId(keyName, workspaceId);

    }
}
