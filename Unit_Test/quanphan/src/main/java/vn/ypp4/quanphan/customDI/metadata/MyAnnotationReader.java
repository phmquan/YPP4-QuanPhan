package vn.ypp4.quanphan.customDI.metadata;


import vn.ypp4.quanphan.customDI.annotation.*;
import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.util.constant.StereoTypeAnnotation;





public class MyAnnotationReader {



    public MyBeanDefinition createBeanDefinition(Class<?> clazz) {
        return StereoTypeAnnotation.getStereotypeList().stream()
                .filter(clazz::isAnnotationPresent)
                .findFirst()
                .map(stereotype -> {
                    String name = defaultBeanName(clazz);

                    String qualifier = clazz.getAnnotation(MyQualifier.class) !=null?
                                        clazz.getAnnotation(MyQualifier.class).value() : null;
                    String scope = clazz.getAnnotation(MyScope.class) != null ?
                            clazz.getAnnotation(MyScope.class).value() : "singleton";
                    return new MyBeanDefinition(clazz, name, qualifier, scope);
                })
                .orElse(null);
    }

    private static String defaultBeanName(Class<?> clazz) {
        return clazz.getSimpleName().substring(0, 1).toLowerCase()
                + clazz.getSimpleName().substring(1);
    }
}
