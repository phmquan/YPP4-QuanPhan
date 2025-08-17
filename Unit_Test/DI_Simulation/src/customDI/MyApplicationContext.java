package customDI;



import customDI.bean.BeanDefinition;
import customDI.bean.BeanDefinitionRegistry;
import customDI.bean.BeanFactory;
import customDI.scanner.ClassPathScanner;

import java.util.List;

public class MyApplicationContext {
    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private final BeanFactory beanFactory;

    public MyApplicationContext(String basePackage) {
        this.beanDefinitionRegistry = new BeanDefinitionRegistry();
        this.beanFactory = new BeanFactory(beanDefinitionRegistry, new customDI.resolver.QualifierResolver(beanDefinitionRegistry));
        ClassPathScanner scanner = new ClassPathScanner();
        List<BeanDefinition> beanDefinitions = scanner.scan(basePackage);
        for (BeanDefinition def : beanDefinitions) {
            beanDefinitionRegistry.register(def);
        }
    }

    public <T> T getBean(Class<T> type) {
        Object bean = beanFactory.getBean(type);
        return type.cast(bean);
    }

    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }
}
