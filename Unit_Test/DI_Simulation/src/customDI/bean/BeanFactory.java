package customDI.bean;

import customDI.annotation.MyAutowired;
import customDI.annotation.MyQualifier;
import customDI.resolver.QualifierResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    private final BeanDefinitionRegistry registry;
    private final Map<String, Object> singletons = new ConcurrentHashMap<>();
    private final ThreadLocal<Set<String>> currentlyCreating =
            ThreadLocal.withInitial(HashSet::new);
    private final QualifierResolver qualifierResolver;

    public BeanFactory(BeanDefinitionRegistry registry, QualifierResolver qualifierResolver) {
        this.qualifierResolver = qualifierResolver;
        this.registry = registry;
    }

    // ============ API ============
    public Object getBean(Class<?> type) {
        if (type.isInterface()) {
            List<BeanDefinition> candidates = registry.findByInterface(type);
            if (candidates.isEmpty()) {
                throw new RuntimeException("No bean found for interface " + type);
            }
            if (candidates.size() > 1) {
                throw new RuntimeException("Ambiguous bean for " + type + " – need @Qualifier");
            }
            return createBeanIfAbsent(candidates.get(0));
        } else {
            BeanDefinition def = registry.findByConcreteClass(type).isPresent()?
                    registry.findByConcreteClass(type).get() : null;;
            if (def == null) throw new RuntimeException("No bean definition for type " + type);
            return createBeanIfAbsent(def);
        }
    }

    public Object getBean(String beanName) {
        BeanDefinition def = registry.findByName(beanName).isPresent()?
                registry.findByName(beanName).get() : null;;
        if (def == null) throw new RuntimeException("No bean with name " + beanName);
        return createBeanIfAbsent(def);
    }

    // ============ Core create logic ============
    private Object createBeanIfAbsent(BeanDefinition def) {
        if ( singletons.containsKey(def.getBeanName())) {
            return singletons.get(def.getBeanName());
        }

        Set<String> creating = currentlyCreating.get();
        if (creating.contains(def.getBeanName())) {
            throw new RuntimeException("Circular dependency detected: " + def.getBeanName());
        }
        creating.add(def.getBeanName());

        try {
            Object instance = instantiate(def);
            injectFields(instance, def);


            singletons.put(def.getBeanName(), instance);

            return instance;
        } finally {
            creating.remove(def.getBeanName());
        }
    }

    // ============ Step 1: Instantiate ============
    private Object instantiate(BeanDefinition def) {
        Constructor<?>[] ctors = def.getConcreteClass().getDeclaredConstructors();
        Constructor<?> chosen = null;

        // Ưu tiên constructor có @Autowired
        for (Constructor<?> ctor : ctors) {
            if (ctor.isAnnotationPresent(MyAutowired.class)) {
                if (chosen != null) {
                    throw new RuntimeException("Multiple @Autowired constructors in " + def.getBeanName());
                }
                chosen = ctor;
            }
        }

        if (chosen == null) {
            try {
                chosen = def.getConcreteClass().getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No default constructor for " + def.getBeanName());
            }
        }

        chosen.setAccessible(true);
        try {
            Object[] args = Arrays.stream(chosen.getParameters())
                    .map(p -> resolveDependency(p.getType(),
                            p.getAnnotation(MyQualifier.class)))
                    .toArray();
            return chosen.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + def.getConcreteClass(), e);
        }
    }

    // ============ Step 2: Field injection ============
    private void injectFields(Object instance, BeanDefinition def) {
        for (Field field : def.getConcreteClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(MyAutowired.class)) continue;

            Object dep = resolveDependency(field.getType(),
                    field.getAnnotation(MyQualifier.class));

            try {
                field.setAccessible(true);
                field.set(instance, dep);
            } catch (Exception e) {
                throw new RuntimeException("Failed to inject " + field + " in " + def.getConcreteClass(), e);
            }
        }
    }

    // ============ Step 3: Resolve dependency ============
    private Object resolveDependency(Class<?> type, MyQualifier qualifier) {
        if (type.isInterface()) {
            BeanDefinition def = qualifierResolver.resolve(type, qualifier);
            return createBeanIfAbsent(def);
        } else {
            if (qualifier != null) {
                return getBean(qualifier.value()); // vẫn cho phép ép buộc bằng name
            }
            return getBean(type);
        }
    }
}
