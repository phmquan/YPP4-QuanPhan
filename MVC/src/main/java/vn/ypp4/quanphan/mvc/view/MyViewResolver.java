package vn.ypp4.quanphan.customMVC.view;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyViewResolver {
    private final String prefix;
    private final String suffix;

    public MyViewResolver(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void render(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (mv == null || mv.getViewName() == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No view specified");
            return;
        }
        mv.getModel().forEach(req::setAttribute);
        String path = prefix + mv.getViewName() + suffix;
        req.getRequestDispatcher(path).forward(req, resp);
    }
}


