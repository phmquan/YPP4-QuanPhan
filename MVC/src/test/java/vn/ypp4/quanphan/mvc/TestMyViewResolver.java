package vn.ypp4.quanphan.mvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vn.ypp4.quanphan.customMVC.view.ModelAndView;
import vn.ypp4.quanphan.customMVC.view.MyViewResolver;

import static org.mockito.Mockito.*;

public class TestMyViewResolver {
    private MyViewResolver viewResolver;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        viewResolver = new MyViewResolver("/templates/", ".jsp");
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
    }

    @Test
    void testRender_Success() throws Exception {
        // given
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("username", "Quan");
        when(request.getRequestDispatcher("/templates/index.jsp"))
                .thenReturn(dispatcher);

        // when
        viewResolver.render(mv, request, response);

        // then
        verify(request).setAttribute("username", "Quan");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testRender_NoViewName() throws Exception {
        // given
        ModelAndView mv = new ModelAndView(null);

        // when
        viewResolver.render(mv, request, response);

        // then
        verify(response).sendError(
                eq(HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
                anyString()
        );
        verifyNoMoreInteractions(dispatcher);
    }
}
