package vn.ypp4.quanphan.customDI.scanner;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import vn.ypp4.quanphan.util.constant.DIAnnotation;
import vn.ypp4.quanphan.util.constant.StereoTypeAnnotation;


import java.lang.annotation.Annotation;
import java.util.Set;

public class MyClassPathScanner {

    public Set<Class<?>> scanStereoType(String basePackage) {
        Reflections reflections = new Reflections(basePackage, Scanners.TypesAnnotated);
        Set<Class<?>> result = new java.util.HashSet<>();

        for (Class<? extends Annotation> annotation : StereoTypeAnnotation.getStereotypeList()) {
            result.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return result; //scan for stereotype
    }


}
