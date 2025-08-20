package vn.ypp4.quanphan.customDI.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyBeanFactory {
    private final Map<String, MyBeanDefinition> beanDefinitions = new ConcurrentHashMap<>();
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();
    private final MyDependencyInjector injector = new MyDependencyInjector(this);

    public void registerBeanDefinition(MyBeanDefinition def) {
        beanDefinitions.put(def.beanName(),def);
    }



    public <T> T getBean(Class<T> type) {

        if (type.isInterface()) {

            // Tìm implementation từ beanDefinitions
            for (MyBeanDefinition candidate : beanDefinitions.values()) {
                System.out.println("Checking candidate: " + candidate.beanClass().getSimpleName());
                if (type.isAssignableFrom(candidate.beanClass()) && !candidate.beanClass().isInterface()) {
                    return createOrGetBean(candidate);
                }
            }
            throw new RuntimeException("No implementation found for interface: " + type.getName());
        }
        return getBean(type,null);
    }

    public <T> T getBean(Class<T> type, String qualifier) {
        System.out.println("getBean called with type: " + type.getSimpleName() + ", qualifier: " + qualifier);

        for(MyBeanDefinition def : beanDefinitions.values()){
            // Kiểm tra exact match hoặc assignable
            boolean typeMatches = type.equals(def.beanClass()) || type.isAssignableFrom(def.beanClass());

            if(typeMatches && !def.beanClass().isInterface()){
                if(qualifier == null || Objects.equals(def.qualifier(), qualifier)){
                    System.out.println("Found bean: " + def.beanName());
                    return createOrGetBean(def);
                }
            }
        }
        throw new RuntimeException("Bean not found for type: " + type.getSimpleName() +
                (qualifier != null ? " with qualifier: " + qualifier : ""));
    }

    public <T> T createOrGetBean(MyBeanDefinition def) {
        String beanName = def.beanName();
        if("singleton".equals(def.scope())){

            return (T) singletonBeans.computeIfAbsent(beanName,n-> createBean(def));
        }
        else{
            return (T) createBean(def);
        }
    }

    public Object createBean(MyBeanDefinition def) {
        try{
            if(def.beanClass().isInterface()){
                return getBean(def.beanClass());
            }
            Object instance= def.beanClass().getDeclaredConstructor().newInstance();
            injector.injectDependencies(instance);
            return instance;
        }
        catch (Exception e){
            throw new RuntimeException("Failed to create bean "+def.beanName(),e);
        }
    }

    // Eager init
    public void initializeSingletons() {
        for(MyBeanDefinition def : beanDefinitions.values()){
            if("singleton".equals(def.scope())){
                createOrGetBean(def);
            }
        }
    }
}
