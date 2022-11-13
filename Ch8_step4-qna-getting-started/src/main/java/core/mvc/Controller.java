package core.mvc;

import core.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
