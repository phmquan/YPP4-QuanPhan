package customDI.resolver;


import customDI.annotation.MyQualifier;
import customDI.bean.BeanDefinition;
import customDI.bean.BeanDefinitionRegistry;

import java.util.List;

public class QualifierResolver {
    private final BeanDefinitionRegistry registry;

    public QualifierResolver(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public BeanDefinition resolve(Class<?> iface, MyQualifier qualifier) {
        if (qualifier != null) {
            String beanName = qualifier.value();
            BeanDefinition def = registry.findByName(beanName).isPresent()?
                    registry.findByName(beanName).get() : null;
            if (def == null) {
                throw new RuntimeException("No such bean name: " + beanName + " for interface " + iface);
            }
            if (!iface.isAssignableFrom(def.getConcreteClass())) {
                throw new RuntimeException("Bean " + beanName + " does not implement " + iface);
            }
            return def;
        }

        List<BeanDefinition> candidates = registry.findByInterface(iface);
        if (candidates.isEmpty()) {
            throw new RuntimeException("No bean found for interface " + iface);
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }

        throw new RuntimeException("Ambiguous dependency for interface " + iface +
                ", expected @Qualifier");
    }
}
