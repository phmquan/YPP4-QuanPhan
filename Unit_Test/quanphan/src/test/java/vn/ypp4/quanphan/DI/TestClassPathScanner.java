package vn.ypp4.quanphan.DI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestClassPathScanner {
    @Test
    void getClassPathScanner() {
        //Arrange
        MyClassPathScanner scanner = new MyClassPathScanner();
        String packageName = "vn.ypp4.quanphan";
        //Act
        Set<Class<?>> result= scanner.scan(packageName);

        //Assert
        assertEquals(2, result.size());
        for(Class<?> c:result){
            System.out.println("Class found: " + c.getName());
        }
    }
}
