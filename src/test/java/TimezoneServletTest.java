
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.TemplateEngine;

import java.io.PrintWriter;


import static org.mockito.Mockito.*;

public class TimezoneServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @InjectMocks
    private TimezoneServlet servlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        TemplateEngine templateEngine = mock(TemplateEngine.class);
        servlet = new TimezoneServlet();
        servlet.init();
        servlet.setTemplateEngine(templateEngine);

        when(response.getWriter()).thenReturn(writer);
    }


    @Test
    public void testDoGet_NoTimezoneParam_NoLastTimezoneCookie() throws Exception {

        when(request.getParameter("timezone")).thenReturn(null);
        when(request.getCookies()).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).setContentType("text/html");
    }

    @Test
    public void testDoGet_NoTimezoneParam_WithLastTimezoneCookie() throws Exception {

        when(request.getParameter("timezone")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).setContentType("text/html");

    }
}
