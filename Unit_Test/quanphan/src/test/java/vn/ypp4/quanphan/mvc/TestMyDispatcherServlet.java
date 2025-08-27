package vn.ypp4.quanphan.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.ypp4.quanphan.customMVC.MyDispatcherServlet;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestMyDispatcherServlet {
    private final MyDispatcherServlet dispatcher=new MyDispatcherServlet();

    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    public TestMyDispatcherServlet() throws ServletException {
    }

    @BeforeEach
    void setUp() throws Exception {
        dispatcher.init();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testHelloMapping() throws Exception {
        when(request.getRequestURI()).thenReturn("/user/hello");
        dispatcher.service(request, response);
        assertEquals("Hello from UserController", responseWriter.toString());
    }

    @Test
    void testDetailWithRequestParam() throws Exception {
        when(request.getRequestURI()).thenReturn("/user/detail");
        when(request.getParameter("id")).thenReturn("123");

        dispatcher.service(request, response);

        assertEquals("User detail for id=123", responseWriter.toString());
    }

    @Test
    void testInjectHttpServletRequest() throws Exception {
        when(request.getRequestURI()).thenReturn("/user/request");
        when(request.getMethod()).thenReturn("GET");

        dispatcher.service(request, response);

        String result = responseWriter.toString();
        assertEquals("Method=GET, URI=/user/request", result);
    }

    @Test
    void testNotFoundHandler() throws Exception {
        when(request.getRequestURI()).thenReturn("/not/exist");

        dispatcher.service(request, response);

        verify(response).sendError(eq(HttpServletResponse.SC_NOT_FOUND), anyString());
    }
}
