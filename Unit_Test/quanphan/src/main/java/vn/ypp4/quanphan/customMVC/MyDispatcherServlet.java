package vn.ypp4.quanphan.customMVC;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;
import vn.ypp4.quanphan.customMVC.handlerAdapter.MyHandlerAdapter;
import java.io.IOException;
import java.util.HashMap;

public class MyDispatcherServlet  {
    private  MyHandlerMapping handlerMapping;
    private  MyHandlerAdapter handlerAdapter;
    private final MyClassPathScanner scanner = new MyClassPathScanner();

    public void init() {
        try {
            handlerMapping = new MyHandlerMapping("vn.ypp4.quanphan");
            handlerAdapter = new MyHandlerAdapter();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String uri = req.getRequestURI();
            MyHandlerMethod handlerMethod = handlerMapping.getHandler(uri);

            if (handlerMethod == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No handler for " + uri);
                return;
            }

            Object result = handlerAdapter.handle(handlerMethod.getController(),handlerMethod.getMethod(), req, new HashMap<>());

            if (result != null) {
                resp.getWriter().write(result.toString());
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
