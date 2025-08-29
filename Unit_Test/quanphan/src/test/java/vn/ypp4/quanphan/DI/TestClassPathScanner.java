package vn.ypp4.quanphan.DI;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;
import vn.ypp4.quanphan.api.util.constant.StereoTypeAnnotation;
import java.util.Arrays;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestClassPathScanner {
    @InjectMocks
    private MyClassPathScanner stereoTypeScanner=new MyClassPathScanner();

    @Test
    void testScanStereoType_WithEmptyStereotypeList() {
        // Given
        String basePackage = "com.example";
        try (MockedStatic<StereoTypeAnnotation> stereotypeMock = mockStatic(StereoTypeAnnotation.class)) {
            // Mock empty stereotype list
            stereotypeMock.when(StereoTypeAnnotation::getStereotypeList)
                    .thenReturn(Arrays.asList());
            // When
            Set<Class<?>> result = stereoTypeScanner.scanStereoType(basePackage);
            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

}
