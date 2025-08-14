package vn.ypp4.quanphan.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import vn.ypp4.quanphan.controller.SettingValueController;
import vn.ypp4.quanphan.domain.dto.SettingValueResponseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TestSettingValue {
    @Autowired
    private SettingValueController settingValueController;
    @Test
    void getSettingValueByKey_Success() {
        // Arrange
        String key = "visibility";
        String expectedValue = "Private";
        int workspaceId = 1; // Assuming workspaceId is required for the method
        //Act
        SettingValueResponseDTO result = settingValueController.getSettingValueForWorkspaceByKeyNameAndId(key,workspaceId);

        // Assert
        assertEquals(expectedValue, result.getDisplayValue());
    }
}
