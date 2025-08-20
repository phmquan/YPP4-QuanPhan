package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestClassPathScanner {
    MyClassPathScanner scanner = new MyClassPathScanner();
    @Test
    void getScanStereoTypeAnnotation() {
        //Arrange

        String packageName = "vn.ypp4.quanphan";
        //Act
        Set<Class<?>> result= scanner.scanStereoType(packageName);
        for(Class<?> c:result){
            System.out.println("Class found: " + c.getName());
        }
        //Assert
        assertEquals(5, result.size());

    }

}
