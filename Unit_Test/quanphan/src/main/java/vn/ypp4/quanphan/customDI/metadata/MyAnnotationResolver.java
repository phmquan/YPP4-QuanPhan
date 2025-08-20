package vn.ypp4.quanphan.customDI.metadata;
import vn.ypp4.quanphan.customDI.annotation.*;
import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.util.constant.StereoTypeAnnotation;

public class MyAnnotationResolver {

    private static String defaultBeanName(Class<?> clazz) {
        return clazz.getSimpleName().substring(0, 1).toLowerCase()
                + clazz.getSimpleName().substring(1);
    }

    public MyBeanDefinition createBeanDefinition(Class<?> clazz) {
        String scope = clazz.getAnnotation(MyScope.class) != null ?
                clazz.getAnnotation(MyScope.class).value() :
                "singleton";
        String qualifier = clazz.getAnnotation(MyQualifier.class)!=null ?
                           clazz.getAnnotation(MyQualifier.class).value() :
                           null;
        return new MyBeanDefinition(clazz, defaultBeanName(clazz),qualifier, scope);
    }

}
