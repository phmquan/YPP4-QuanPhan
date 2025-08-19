package vn.ypp4.quanphan.customDI.context;

import vn.ypp4.quanphan.customDI.core.MyBeanDefinition;
import vn.ypp4.quanphan.customDI.core.MyBeanFactory;
import vn.ypp4.quanphan.customDI.metadata.MyAnnotationReader;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;

import java.util.Set;

public class MyApplicationContext {
    private final MyBeanFactory beanFactory = new MyBeanFactory();

    public MyApplicationContext(String basePackage) {
        // 1. Scan package để tìm class có annotation  @Controller
        MyClassPathScanner scanner = new MyClassPathScanner();
        Set<Class<?>> candidates = scanner.scan(basePackage);

        // 2. Tạo BeanDefinition và register vào BeanFactory
        MyAnnotationReader reader = new MyAnnotationReader();
        for (Class<?> clazz : candidates) {
            MyBeanDefinition def = reader.createBeanDefinition(clazz);
            beanFactory.registerBeanDefinition(def);
        }
//
        // 3. Khởi tạo singleton bean (eager init)
        beanFactory.initializeSingletons();
    }

    // 4. API để lấy bean ra ngoài
    public <T> T getBean(Class<T> type) {
        return beanFactory.getBean(type);
    }

    public <T> T getBean(Class<T> type, String qualifier) {
        return beanFactory.getBean(type, qualifier);
    }
}
