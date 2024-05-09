import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieHandler {

    public static final String TIMEZONE_COOKIE_NAME = "lastTimezone";

    public static String getLastTimezone(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + " : " + cookie.getValue());
                if (cookie.getName().equals(TIMEZONE_COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }

        return "UTC";
    }

    public static void setLastTimezone(HttpServletResponse response, String timezone) {
        Cookie cookie = new Cookie(TIMEZONE_COOKIE_NAME, timezone);
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
