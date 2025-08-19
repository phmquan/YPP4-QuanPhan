package vn.ypp4.quanphan.customDI.core;

public class MyBeanDefinition {
    private final Class<?> beanClass;
    private final String beanName;   // định danh bean trong container
    private final String qualifier;  // chỉ định inject cụ thể (nếu có)
    private final String scope;      // "singleton" hoặc "prototype"

    public MyBeanDefinition(Class<?> beanClass, String beanName, String qualifier, String scope) {
        this.beanClass = beanClass;
        this.beanName = beanName;
        this.qualifier = qualifier;
        this.scope = scope;
    }
    public MyBeanDefinition(Class<?> beanClass, String beanName,String scope) {
        this(beanClass, beanName, null, scope);
    }

    public Class<?> getBeanClass() { return beanClass; }
    public String getBeanName() { return beanName; }
    public String getQualifier() { return qualifier; }
    public String getScope() { return scope; }
}
