package vn.ypp4.quanphan.customMVC;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ModelAndView {
    @Setter
    private String viewName;
    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public void addObject(String key, Object value) {
        model.put(key, value);
    }
}

