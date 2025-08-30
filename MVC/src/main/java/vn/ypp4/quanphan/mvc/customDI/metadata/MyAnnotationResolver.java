package vn.ypp4.quanphan.mvc.customDI.metadata;
import vn.ypp4.quanphan.mvc.customDI.annotation.*;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyQualifier;
import vn.ypp4.quanphan.mvc.customDI.annotation.MyScope;
import vn.ypp4.quanphan.mvc.customDI.core.MyBeanDefinition;


public class MyAnnotationResolver {
    private static String defaultBeanName(Class<?> clazz) {
        return clazz.getSimpleName().substring(0, 1).toLowerCase()
                + clazz.getSimpleName().substring(1);
    }

    /***
     * @param clazz
     * @return
     */
    public MyBeanDefinition createBeanDefinition(Class<?> clazz) {
        String scope = clazz.getAnnotation(MyScope.class) != null ?
                clazz.getAnnotation(MyScope.class).value() :
                "singleton";
        Class<?>[] interfaces = clazz.getInterfaces();
        String implementedFrom = interfaces.length > 0 ? interfaces[0].getName() : null;
        String qualifier = clazz.getAnnotation(MyQualifier.class)!=null ?
                           clazz.getAnnotation(MyQualifier.class).value() :
                           null;
        return new MyBeanDefinition(clazz, defaultBeanName(clazz),implementedFrom,qualifier, scope);
    }

}
