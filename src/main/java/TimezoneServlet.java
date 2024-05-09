import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimezoneServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void init() {
        templateEngine = new TemplateEngine();

        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("C:/Users/Oksana/java-core-17/thymeleaf/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String timeZoneParam = request.getParameter("timezone");

        if (timeZoneParam == null) {
            timeZoneParam = "";
        } else {
            timeZoneParam = URLDecoder.decode(timeZoneParam, StandardCharsets.UTF_8);
            timeZoneParam = timeZoneParam.replace(" ", "+");
        }

        String lastTimezone = CookieHandler.getLastTimezone(request);

        if (timeZoneParam.isEmpty()) {
            timeZoneParam = lastTimezone;
        } else {
            CookieHandler.setLastTimezone(response, timeZoneParam);
        }

        String currentTime = "";
        if (!timeZoneParam.isEmpty()) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZoneParam));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            currentTime = zonedDateTime.format(formatter);
        }

        Context context = new Context();
        context.setVariable("currentTime", currentTime);
        context.setVariable("timeZone", timeZoneParam);

        String htmlContent = templateEngine.process("time-thymeleaf", context);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(htmlContent + " " + timeZoneParam);

    }
}
