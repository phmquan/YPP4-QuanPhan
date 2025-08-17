package customDI.bean;

public class DependencyDescriptor {
    private final Class<?> type;
    private final String qualifier; // optional

    public DependencyDescriptor(Class<?> type, String qualifier) {
        this.type = type;
        this.qualifier = qualifier;
    }

}

