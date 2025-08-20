package vn.ypp4.quanphan.customDI.context;

import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.customDI.core.MyBeanFactory;

import vn.ypp4.quanphan.customDI.metadata.MyAnnotationResolver;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.util.Set;

public class MyApplicationContext {
    private final MyBeanFactory beanFactory = new MyBeanFactory();

    public MyApplicationContext(String basePackage) {

        MyClassPathScanner scanner = new MyClassPathScanner();
        Set<Class<?>> stereoTypeClass = scanner.scanStereoType(basePackage);

        MyAnnotationResolver resolver = new MyAnnotationResolver();
        for (Class<?> clazz : stereoTypeClass) {
            MyBeanDefinition def = resolver.createBeanDefinition(clazz);
            beanFactory.registerBeanDefinition(def);
        }

        beanFactory.initializeSingletons();
    }


    public <T> T getBean(Class<T> type) {
        return beanFactory.getBean(type);
    }

    public <T> T getBean(Class<T> type, String qualifier) {
        return beanFactory.getBean(type, qualifier);
    }
}
