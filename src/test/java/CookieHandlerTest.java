import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CookieHandlerTest {

    @Test
    public void testGetLastTimezone_WithValidCookie() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie(CookieHandler.TIMEZONE_COOKIE_NAME, "UTC+2");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        String lastTimezone = CookieHandler.getLastTimezone(request);

        assertEquals("UTC+2", lastTimezone);
    }

    @Test
    public void testGetLastTimezone_WithNullCookies() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(null);

        String lastTimezone = CookieHandler.getLastTimezone(request);

        assertEquals("UTC", lastTimezone);
    }

    @Test
    public void testGetLastTimezone_WithNoMatchingCookie() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("otherCookie", "value");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        String lastTimezone = CookieHandler.getLastTimezone(request);

        assertEquals("UTC", lastTimezone);
    }

    @Test
    public void testSetLastTimezone() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        CookieHandler.setLastTimezone(response, "UTC+3");

    }
}
