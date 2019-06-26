package by.platonov.music.controller;

import by.platonov.music.command.*;
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

    private final CommandFactory commandFactory = CommandFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent content = new RequestContent(request);
        Command command = commandFactory.getCommand(content);
        CommandResult commandResult = command.execute(content);

        commandResult.getAttributes().forEach(request::setAttribute);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);

        if (command.getClass().isAssignableFrom(LogoutCommand.class)) {
            request.getSession().invalidate();
        }

        if (command.getClass().isAssignableFrom(LoginCommand.class)) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        }

        if (commandResult.getResponseType() == CommandResult.ResponseType.FORWARD) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(commandResult.getPage());
        }
    }
}
