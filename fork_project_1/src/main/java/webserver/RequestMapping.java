package webserver;

import java.util.HashMap;
import java.util.Map;

/**
 * 웹 서비스에 URL과 Controller를 관리
 * 요청 URL에 해당하는 Controller를 반환하는 역할
 *
 * @param requestUrl
 * */
public class RequestMapping {

    public static Map<String, Controller> controllers =
            new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
    }

    public static Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }

}
