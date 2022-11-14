package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.AbstractController;
import core.mvc.Controller;
import core.view.JsonView;
import core.view.ModelAndView;
import core.view.View;
import next.dao.AnswerDao;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class AddAnswerController extends AbstractController {
    public static final Logger log  = LoggerFactory.getLogger(AddAnswerController.class);

    /**
     * JSON 데이터를 생성해 바로 응답을 보내기 때문에
     * 이동할 페이지가 없어 null 반환
     *  > DispatcherSevlet이 null처리를 하지 않고 있기 때문에 뷰 이름이 null인 경우
     *  > 페이지 이동을 하지 않음
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Answer answer = new Answer(req.getParameter("writer"),
                                    req.getParameter("contents"),
                                        Long.parseLong(req.getParameter("questionId")));
        log.debug("answer: {}", answer);

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);

        req.setAttribute("answer", savedAnswer);

        /*ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(savedAnswer));*/
        return jsonView().addObject("answer", savedAnswer);
    }
}
