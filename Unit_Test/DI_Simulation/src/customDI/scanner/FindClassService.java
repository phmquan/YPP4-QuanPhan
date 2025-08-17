package customDI.scanner;



import customDI.annotation.MyComponent;
import customDI.annotation.MyRepository;
import customDI.annotation.MyService;

import java.io.File;
import java.lang.reflect.Modifier;

import java.util.List;


public class FindClassService {

    public void findClassesInDir(String pkg, File dir, List<Class<?>> result) {
        if (!dir.exists()) return;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // đệ quy vào sub-package
                findClassesInDir(pkg + "." + file.getName(), file, result);
            } else if (file.getName().endsWith(".class")) {
                String className = pkg + '.' + file.getName().replace(".class", "");
                tryAddCandidate(className, result);
            }
        }
    }
    public void tryAddCandidate(String className, List<Class<?>> result) {
        try {
            Class<?> clazz = Class.forName(className);

            // Lọc theo annotation
            if (!(clazz.isAnnotationPresent(MyComponent.class)
                    || clazz.isAnnotationPresent(MyService.class)
                    || clazz.isAnnotationPresent(MyRepository.class)
                    || clazz.isAnnotationPresent(vn.ypp4.quanphan.customDI.annotation.MyController.class))) {
                return;
            }

            // Loại bỏ abstract, interface, inner non-static
            int mod = clazz.getModifiers();
            if (Modifier.isAbstract(mod)) return;
            if (clazz.isInterface()) return;
            if (clazz.isMemberClass() && !Modifier.isStatic(mod)) return;

            result.add(clazz);
        } catch (Throwable e) {

        }
    }
}
