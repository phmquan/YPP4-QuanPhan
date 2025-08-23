package vn.ypp4.quanphan.mvc;

import org.junit.jupiter.api.Test;
import vn.ypp4.quanphan.customDI.annotation.MyController;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMyClassPathScanner {

    @Test
    void testScanControllersInPackage() {
        // Arrange
        MyClassPathScanner scanner = new MyClassPathScanner();
        String basePackage = "vn.ypp4.quanphan"; // đổi thành package project của bạn

        // Act
        Set<Class<?>> controllers = scanner.scanForControllers(basePackage);

        // Assert
        assertTrue(
                controllers.stream().allMatch(c -> c.isAnnotationPresent(MyController.class)),
                "All found classes must have @MyController"
        );
    }
}
