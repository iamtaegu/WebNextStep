package core.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import next.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JsonView implements View {
    public static final Logger log = LoggerFactory.getLogger(JsonView.class);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        log.debug("mapper.writeValueAsString(createModel(req)): {}", mapper.writeValueAsString(createModel(req)));

        out.print(mapper.writeValueAsString(createModel(req)));
    }

    private Map<String, Object> createModel(HttpServletRequest req) {
        Enumeration<String> names = req.getAttributeNames();
        Map<String, Object> model = new HashMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            model.put(name, req.getAttribute(name));
        }
        return model;
    }
}
