package customDI.bean;

import java.lang.reflect.Modifier;
import java.util.*;


public class BeanDefinitionRegistry {
    Map<Class<?>, BeanDefinition> byConcreteClass;
    Map<String, BeanDefinition> byName;
    Map<Class<?>, List<BeanDefinition>> byInterface;

    public BeanDefinitionRegistry() {
        byConcreteClass = new HashMap<>();
        byName = new HashMap<>();
        byInterface = new HashMap<>();
    }

    public void register(BeanDefinition def){
        Objects.requireNonNull(def, "BeanDefinition cannot be null");
        String name = Objects.requireNonNull(def.getBeanName(), "beanName cannot be null");
        Class<?> concrete = Objects.requireNonNull(def.getConcreteClass(), "concreteClass cannot be null");
        // Edge case: concrete class must not be an interface or abstract class
        if(concrete.isInstance(concrete) || Modifier.isAbstract(concrete.getModifiers())) {
            throw new IllegalArgumentException("Concrete class must be a concrete class, not an interface or abstract class");
        }

        System.out.println("Registering bean: " + name + " (" + concrete.getName() + ")");
        BeanDefinition existedBeanName= byName.putIfAbsent(name,def);
        if(existedBeanName != null){
            throw new IllegalArgumentException("Concrete bean name must be unique");
        }

        BeanDefinition existed= byConcreteClass.putIfAbsent(concrete, def);
        if(existed != null){
            byConcreteClass.put(concrete, existed);
        }

        for (Class<?> itf : getAllInterfaces(concrete)) {
            System.out.println("  -> Maps to interface: " + itf.getName());
            List<BeanDefinition> list=byInterface.get(itf);
            if (list == null) {
                list = new java.util.ArrayList<>();
                byInterface.put(itf, list);
            }
            list.add(def);
        }
    }
    public Optional<BeanDefinition> findByConcreteClass(Class<?> type){
        return Optional.of(byConcreteClass.get(type));
    }
    public Optional <BeanDefinition> findByName(String name){
        return Optional.ofNullable(byName.get(name));
    }
    public List<BeanDefinition> findByInterface(Class<?> itf) {
        List<BeanDefinition> result= byInterface.get(itf);
        return (result == null) ? Collections.emptyList()
                                : Collections.unmodifiableList(result);
    }
    private static Set<Class<?>> getAllInterfaces(Class<?> clazz) {
        Set<Class<?>> interfaces = new HashSet<>();
        while (clazz != null) {
            interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }
}
