package customDI.bean;





import java.util.List;


public class BeanDefinition {
    private String beanName;
    private Class<?> concreteClass;
    List<Class<?>> beanClassList;
    List<DependencyDescriptor> dependencyList;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getConcreteClass() {
        return concreteClass;
    }

    public void setConcreteClass(Class<?> concreteClass) {
        this.concreteClass = concreteClass;
    }

    public List<Class<?>> getBeanClassList() {
        return beanClassList;
    }

    public void setBeanClassList(List<Class<?>> beanClassList) {
        this.beanClassList = beanClassList;
    }

    public List<DependencyDescriptor> getDependencyList() {
        return dependencyList;
    }

    public void setDependencyList(List<DependencyDescriptor> dependencyList) {
        this.dependencyList = dependencyList;
    }

    public BeanDefinition(String beanName, Class<?> concreteClass, List<Class<?>> beanClassList, List<DependencyDescriptor> dependencyList) {
        this.beanName = beanName;
        this.concreteClass = concreteClass;
        this.beanClassList = beanClassList;
        this.dependencyList = dependencyList;
    }
}
