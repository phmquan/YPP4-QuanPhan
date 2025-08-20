package vn.ypp4.quanphan.customDI.core;

import java.util.*;

public class MyBeanFactory {
    private final Map<String, MyBeanDefinition> beanDefinitions = new HashMap<>();
    private final Map<String, Object> singletonBeans = new HashMap<>();
    private final MyDependencyInjector injector = new MyDependencyInjector(this);

    public void registerBeanDefinition(MyBeanDefinition def) {
        beanDefinitions.put(def.beanName(),def);
    }

    public <T> T getBean(Class<T> type) {
        return getBean(type,null);
    }

    public <T> T getBean(Class<T> type, String qualifier) {
        for(MyBeanDefinition def : beanDefinitions.values()){
            if(type.isAssignableFrom(def.beanClass())){
                if(qualifier==null||def.qualifier().equals(qualifier)){
                    return createOrGetBean(def);
                }
            }
        }
        throw new RuntimeException("bean not found");
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
