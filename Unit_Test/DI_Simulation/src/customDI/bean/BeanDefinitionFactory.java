package customDI.bean;





import customDI.annotation.MyAutowired;
import customDI.annotation.MyQualifier;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanDefinitionFactory {
    public BeanDefinition createBeanDefinition(Class<?> beanClass) {
        String beanName = resolveBeanName(beanClass);
        List<Class<?>> interfaces = Arrays.asList(beanClass.getInterfaces());
        List<DependencyDescriptor> dependencies = resolveDependencies(beanClass);

        return new BeanDefinition(beanName, beanClass, interfaces, dependencies);
    }

    private String resolveBeanName(Class<?> beanClass) {

        return Introspector.decapitalize(beanClass.getSimpleName());
    }

    private List<DependencyDescriptor> resolveDependencies(Class<?> beanClass) {
        List<DependencyDescriptor> deps = new ArrayList<>();

        for (Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                String qualifier = null;
                if (field.isAnnotationPresent(MyQualifier.class)) {
                    qualifier = field.getAnnotation(MyQualifier.class).value();
                }
                deps.add(new DependencyDescriptor(field.getType(), qualifier));
            }
        }

        return deps;
    }
}
