package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

public class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(WebServer.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, port : {}"
                , connection.getInetAddress()
                , connection.getPort());

        try (InputStream in = connection.getInputStream();
                OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            String path = getDafaultPath(request.getPath());

            log.debug("[RequestHandler] path ={}", path);

            // [2. 리팩토링] 분기문 구현을 다형성을 활용해 객체로 분리
            Controller controller = RequestMapping.getController(path);

            if (controller == null) {
                response.forward(getDafaultPath(request.getPath()));
            } else {
                controller.service(request, response);
            }

            // [1. 리팩토링] 분기문 구현을 메소드로 분리
            /*if ("/user/create".equals(path)) {
                createUser(request, response);
            } else if ("/user/login".equals(path)) {
                login(request, response);
            } else if ("/user/list".equals(path)) {
                listUser(request, response);
            } else {
                response.forward(path);
            }
            */
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void listUser(HttpRequest request, HttpResponse response) {
        if (!request.isLogin(request.getHeaders("Cookie"))) {
            response.sendRedirect("/user/login.html");
            return;
        }
        Collection<User> users = DataBase.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<td>"+ user.getUserId() +"</td>");
            sb.append("<td>"+ user.getName() +"</td>");
            sb.append("<td>"+ user.getEmail() +"</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        response.forwardBody(sb.toString());
    }

    private void login(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user != null) {
            if (user.getPassword().equals(request.getParameter("password"))) {
                response.addHeader("Set-Cookie", "logined=true");
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect("/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId")
                , request.getParameter("password")
                , request.getParameter("name")
                , request.getParameter("email"));
        DataBase.addUser(user);
        //서버에서 forward 하면서 새로고침 이슈 발생
        response.sendRedirect("/index.html");
    }

    private String getDafaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }

    private int getContentLength(String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
    }
}
