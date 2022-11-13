package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.view.JspView;
import core.view.View;
import next.dao.QuestionDao;

public class HomeController implements Controller {
    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        req.setAttribute("questions", questionDao.findAll());
        return new JspView("home.jsp");
    }
}
