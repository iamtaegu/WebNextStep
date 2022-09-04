package webserver;

import db.DataBase;
import model.User;

import java.util.Collection;

public class ListUserController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
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
}
