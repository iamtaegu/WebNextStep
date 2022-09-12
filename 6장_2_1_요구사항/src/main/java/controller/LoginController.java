package controller;

import http.HttpSession;
import http.HttpSessions;
import model.User;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user != null) {
            if (user.login(request.getParameter("password"))) {
                /**
                 * 로그인 후에 쿠키에 값을 넣어 주는게 아니라
                 * response.addHeader("Set-Cookie", "logined=true");
                 * 로그인 후에 세션을 생성하고 세션에 User 객체를 넣어줌
                 *
                 *  */
                HttpSession session = request.getSession();
                session.setAttributes("user", user);
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect("/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
