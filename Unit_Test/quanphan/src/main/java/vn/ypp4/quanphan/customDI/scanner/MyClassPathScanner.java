package vn.ypp4.quanphan.customDI.scanner;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import vn.ypp4.quanphan.customDI.annotation.MyController;
import vn.ypp4.quanphan.api.util.constant.StereoTypeAnnotation;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MyClassPathScanner {

    public Set<Class<?>> scanStereoType(String basePackage) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);
        return StereoTypeAnnotation.getStereotypeList().stream()
                .flatMap(annotation -> reflections.getTypesAnnotatedWith(annotation).stream())
                .collect(Collectors.toSet()); //scan for stereotype
    }


    public Set<Class<?>> scanForControllers(String basePackage) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);

        return new HashSet<>(reflections.getTypesAnnotatedWith(MyController.class));
    }
    public Set<Method> scanMethodsWithAnnotation(String basePackage,Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);
        return new HashSet<>(reflections.getMethodsAnnotatedWith(annotation));
    }
}
