package customDI.scanner;






import customDI.bean.BeanDefinition;
import customDI.bean.BeanDefinitionFactory;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLDecoder;
import java.util.*;


public class ClassPathScanner {
    private final FindClassService findClassService = new FindClassService();
    private final BeanDefinitionFactory beanDefinitionFactory = new BeanDefinitionFactory();


    public List<BeanDefinition> scan(String basePackage) {
        List<Class<?>> candidates = findCandidateClasses(basePackage);
        List<BeanDefinition> beanDefinitions = new ArrayList<>();

        for (Class<?> candidate : candidates) {
            BeanDefinition def = beanDefinitionFactory.createBeanDefinition(candidate);
            beanDefinitions.add(def);
        }

        return beanDefinitions;
    }

    private List<Class<?>> findCandidateClasses(String basePackage) {
        List<Class<?>> candidates = new ArrayList<>();
        try {
            String path = basePackage.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");
                File dir = new File(filePath);
                findClassService.findClassesInDir(basePackage, dir, candidates);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan package " + basePackage, e);
        }
        return candidates;
    }

}
