package vn.ypp4.quanphan.customMVC.customDI.core;
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
            return (T) beanDefinitions.values().stream()
                    .filter(candidate ->
                            type.isAssignableFrom(candidate.beanClass()) &&
                            !candidate.beanClass().isInterface())
                    .findFirst()
                    .map(this::createOrGetBean)
                    .orElseThrow(() -> new RuntimeException(
                            "No implementation found for interface: " + type.getName()));
        }
        return getBean(type,null);
    }

    public <T> T getBean(Class<T> type, String qualifier) {
        return (T) beanDefinitions.values().stream()
                .filter(def -> {
                    boolean typeMatches = type.equals(def.beanClass()) || type.isAssignableFrom(def.beanClass());
                    return typeMatches && !def.beanClass().isInterface();
                })
                .filter(def -> qualifier == null || Objects.equals(def.qualifier(), qualifier))
                .peek(def -> System.out.println("Found bean: " + def.beanName()))
                .findFirst()
                .map(this::createOrGetBean)
                .orElseThrow(() -> new RuntimeException(
                        "Bean not found for type: " + type.getSimpleName() +
                                (qualifier != null ? " with qualifier: " + qualifier : "")
                ));

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

    /***
     *
     *
     */
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

    /***
     *
     */
    public void initializeSingletons() {
        beanDefinitions.values().stream()
                .filter(def -> "singleton".equals(def.scope()))
                .forEach(this::createOrGetBean);
    }
}
