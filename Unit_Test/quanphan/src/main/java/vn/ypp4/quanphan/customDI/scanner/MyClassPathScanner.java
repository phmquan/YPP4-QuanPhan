package vn.ypp4.quanphan.customDI.scanner;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import vn.ypp4.quanphan.customDI.annotation.MyController;

import java.util.Set;

public class MyClassPathScanner {
    public Set<Class<?>> scan(String basePackage) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);
        return reflections.getTypesAnnotatedWith(MyController.class);
    }
}
