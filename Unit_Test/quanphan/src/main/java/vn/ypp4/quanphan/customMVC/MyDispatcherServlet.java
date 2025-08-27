package vn.ypp4.quanphan.customMVC;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ypp4.quanphan.customDI.container.MyApplicationContext;
import vn.ypp4.quanphan.customDI.scanner.MyClassPathScanner;
import vn.ypp4.quanphan.customMVC.handlerAdapter.MyHandlerAdapter;
import java.io.IOException;
import java.util.Collections;

public class MyDispatcherServlet  {
    private  MyHandlerMapping handlerMapping;
    private  MyHandlerAdapter handlerAdapter;

    public void init() {
        try {
            MyApplicationContext myApplicationContext = new MyApplicationContext("vn.ypp4.quanphan");
            handlerMapping = new MyHandlerMapping("vn.ypp4.quanphan", myApplicationContext);
            handlerAdapter = new MyHandlerAdapter();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String httpMethod = req.getMethod();

        MyHandlerMethod handlerMethod = handlerMapping.getHandler(uri, httpMethod);
        if (handlerMethod == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No handler found for " + uri);
            return;
        }

        try {
            Object result = handlerAdapter.handle(
                    handlerMethod.getController(),
                    handlerMethod.getMethod(),
                    req,
                    Collections.emptyMap()
            );
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(result != null ? result.toString() : "");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }
}
