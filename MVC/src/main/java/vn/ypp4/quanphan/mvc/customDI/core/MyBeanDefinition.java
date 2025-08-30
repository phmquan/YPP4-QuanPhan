package vn.ypp4.quanphan.customMVC.customDI.core;


public record MyBeanDefinition(Class<?> beanClass, String beanName,String implementedFrom, String qualifier, String scope) {
    public MyBeanDefinition(Class<?> beanClass, String beanName, String scope) {
        this(beanClass, beanName,null, null, scope);
    }
}
