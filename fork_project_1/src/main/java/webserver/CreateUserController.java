package webserver;

import db.DataBase;
import model.User;

public class CreateUserController extends AbstractController //implements Controller {
{
    /* @Override
    public void service(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId")
                , request.getParameter("password")
                , request.getParameter("name")
                , request.getParameter("email"));
        DataBase.addUser(user);
        //서버에서 forward 하면서 새로고침 이슈 발생
        response.sendRedirect("/index.html");
    }*/

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId")
                , request.getParameter("password")
                , request.getParameter("name")
                , request.getParameter("email"));
        DataBase.addUser(user);
        //서버에서 forward 하면서 새로고침 이슈 발생
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }


}
