package by.platonov.music.controller;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandFactory;
import by.platonov.music.command.CommandResult;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
@Log4j2
@WebServlet(urlPatterns = "/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
//        resp.setContentType("text/html");
//        resp.getWriter().print("This is " + this.getClass().getName()
//                + ", using the GET method");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("inside do post");
        processRequest(req, resp);
//        String login = req.getParameter("login");
//        String password = req.getParameter("password");
//        String commandString = req.getParameter("command");
//        RequestContent content = new RequestContent();

//        CommandFactory commandFactory = CommandFactory.getInstance();
//        Command command = commandFactory.getCommand(content);
//        CommandResult commandResult = command.execute(content);
//        String page = commandResult.getPage();

//        if(page != null && commandResult.getResponseType() == CommandResult.ResponseType.FORWARD) {
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher(page);
//            requestDispatcher.forward(req, resp);
//        } else {
//            resp.sendRedirect("index.jsp");
//        }

//        resp.setContentType("text/html");
//        resp.getWriter().print("This is " + this.getClass().getName()
//                + ", using the POST method");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("inside request");
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(request);
        CommandResult commandResult = command.execute(request);

        if (commandResult.getResponseType() == CommandResult.ResponseType.FORWARD) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(commandResult.getPage());
        }
    }
}
