package vn.ypp4.quanphan.customDI.container;

import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.customDI.core.MyBeanFactory;

import vn.ypp4.quanphan.customDI.metadata.MyAnnotationResolver;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.util.Set;

public class MyApplicationContext {
    private final MyBeanFactory beanFactory = new MyBeanFactory();

    public MyApplicationContext(String basePackage) {
        MyClassPathScanner scanner = new MyClassPathScanner();
        Set<Class<?>> stereoTypeClassSet = scanner.scanStereoType(basePackage);
        MyAnnotationResolver resolver = new MyAnnotationResolver();

        stereoTypeClassSet.stream()
                .filter(clazz -> !clazz.isInterface())
                .map(resolver::createBeanDefinition)
                .forEach(beanFactory::registerBeanDefinition);
        beanFactory.initializeSingletons();
    }


    public <T> T getBean(Class<T> type) {
        return beanFactory.getBean(type);
    }

}
