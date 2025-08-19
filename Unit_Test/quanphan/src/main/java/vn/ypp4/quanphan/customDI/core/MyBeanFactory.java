package vn.ypp4.quanphan.customDI.core;

import vn.ypp4.quanphan.customDI.annotation.MyAutowired;
import vn.ypp4.quanphan.customDI.annotation.MyQualifier;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MyBeanFactory {
    private final Map<String,MyBeanDefinition> beanDefinitions = new HashMap<>();
    private final Map<String,Object> singletonObjects=new HashMap<>();

    public void registerBeanDefinition(MyBeanDefinition def) {
        beanDefinitions.put(def.getBeanName(),def);
    }

    public void initializeSingletons() {
        for (String beanName : beanDefinitions.keySet()) {
            MyBeanDefinition def = beanDefinitions.get(beanName);
            if("singleton".equals(def.getScope())) {
                getBean(def.getBeanClass());
            }
        }
    }

    public <T> T getBean(Class<T> type) {
        for(MyBeanDefinition def : beanDefinitions.values()) {
            if(type.isAssignableFrom(def.getBeanClass())) {
                return createOrGetBean(def,null,type);
            }
        }
        throw new RuntimeException("No bean found for type " + type.getName());
    }



    public <T> T getBean(Class<T> type, String qualifier) {
        for(MyBeanDefinition def : beanDefinitions.values()) {
            if(type.isAssignableFrom(def.getBeanClass()) && qualifier.equals(def.getQualifier())) {
                return createOrGetBean(def,null,type);
            }
        }
        throw new RuntimeException("No bean found for type " + type.getName() + " with qualifier=" + qualifier);
    }
    @SuppressWarnings("unchecked")
    private <T> T createOrGetBean(MyBeanDefinition def, String qualifier, Class<T> requiredType) {
        if ("singleton".equals(def.getScope())) {
            if (singletonObjects.containsKey(def.getBeanName())) {
                return (T) singletonObjects.get(def.getBeanName());
            }
        }

        try {
            // Tạo instance
            Object instance = def.getBeanClass().getDeclaredConstructor().newInstance();

            // Dependency injection (field-based)
            for (Field field : def.getBeanClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(MyAutowired.class)) {
                    field.setAccessible(true);

                    String fieldQualifier = null;
                    if (field.isAnnotationPresent(MyQualifier.class)) {
                        fieldQualifier = field.getAnnotation(MyQualifier.class).value();
                    }

                    Object dependency = (fieldQualifier != null)
                            ? getBean(field.getType(), fieldQualifier)
                            : getBean(field.getType());

                    field.set(instance, dependency);
                }
            }

            // Lưu singleton
            if ("singleton".equals(def.getScope())) {
                singletonObjects.put(def.getBeanName(), instance);
            }

            return (T) instance;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean: " + def.getBeanClass().getName(), e);
        }
    }
}
