package vn.ypp4.quanphan.customMVC;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ypp4.quanphan.customDI.container.MyApplicationContext;
import vn.ypp4.quanphan.customMVC.handlerAdapter.MyHandlerAdapter;
import vn.ypp4.quanphan.customMVC.view.ModelAndView;
import vn.ypp4.quanphan.customMVC.view.MyViewResolver;

import java.io.IOException;
import java.util.Collections;

public class MyDispatcherServlet  {
    private  MyHandlerMapping handlerMapping;
    private  MyHandlerAdapter handlerAdapter;
    private MyViewResolver viewResolver;

    public void init(String suffix,String prefix,String basePackage) {
        try {
            MyApplicationContext myApplicationContext = new MyApplicationContext(basePackage);
            handlerMapping = new MyHandlerMapping(basePackage, myApplicationContext);
            handlerAdapter = new MyHandlerAdapter();
            viewResolver = new MyViewResolver(prefix, suffix);
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

            if (result instanceof ModelAndView mv) {
                viewResolver.render(mv, req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }
}
