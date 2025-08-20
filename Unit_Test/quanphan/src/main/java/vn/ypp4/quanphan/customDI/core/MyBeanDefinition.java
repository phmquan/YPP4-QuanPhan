package vn.ypp4.quanphan.customDI.core;


public record MyBeanDefinition(Class<?> beanClass, String beanName, String qualifier, String scope) {

    public MyBeanDefinition(Class<?> beanClass, String beanName, String scope) {
        this(beanClass, beanName, null, scope);
    }
}
