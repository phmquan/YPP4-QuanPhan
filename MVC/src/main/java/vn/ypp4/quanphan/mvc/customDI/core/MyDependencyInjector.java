package vn.ypp4.quanphan.customMVC.customDI.core;

import vn.ypp4.quanphan.customMVC.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customMVC.customDI.annotation.MyQualifier;

import java.lang.reflect.Field;

public class MyDependencyInjector {
    private final MyBeanFactory beanFactory;

    public MyDependencyInjector(MyBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void injectDependencies(Object bean) {
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                Object dependency = resolveDependency(field);
                field.setAccessible(true);
                try {
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject dependency into "
                            + clazz.getName() + "." + field.getName(), e);
                }
            }
        }
    }

    private Object resolveDependency(Field field) {
        Class<?> type = field.getType();
        // Check for qualifier
        MyQualifier qualifier = field.getAnnotation(MyQualifier.class);
        if (qualifier != null) {
            return beanFactory.getBean(type, qualifier.value());
        } else {
            return beanFactory.getBean(type);
        }
    }
}


