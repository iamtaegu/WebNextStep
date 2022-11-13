package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import core.view.JsonView;
import core.view.View;
import next.dao.AnswerDao;
import next.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {

    public static final Logger log  = LoggerFactory.getLogger(DeleteAnswerController.class);


    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("answerId"));

        AnswerDao answerDao = new AnswerDao();

        answerDao.delete(questionId);

        req.setAttribute("status", Result.ok());

        /*ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(Result.ok()));*/
        return new JsonView();
    }
}
