package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod method;
    private String path;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();

    /**
     * InputStream에 필요한 데이터를 객체의 필드에 저장하는 역할만 처리
     *
     * @param InputStream
     * */
    public HttpRequest(InputStream in) {
        try {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            if (line == null) {
                return;
            }

            processRequestLine(line);

            line = br.readLine();
            while(!line.equals("")) {
                log.debug("header : {}", line);
                String[] tokens = line.split(":");
                headers.put(tokens[0].trim(), tokens[1].trim());
                line = br.readLine();
            }

            if (method.isPost()) {
                String body = IOUtils.readData(br
                        , Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            }

        } catch (Exception e) {

        }
    }

    private void processRequestLine(String requestLine) {
        RequestLine rl = new RequestLine(requestLine);
        method = rl.getMethod();
        path = rl.getPath();
        params = rl.getParams();
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHeaders(String name) {
        return headers.get(name);
    }

    public String getParams(String name) {
        return params.get(name);
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public boolean isLogin(String line) {
        String[] headerTokens = line.split(":");
        Map<String, String> cookies =
                HttpRequestUtils.parseCookies(headerTokens[1].trim());
        String value = cookies.get("logined");
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}
