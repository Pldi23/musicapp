package by.platonov.music.controller;

import by.platonov.music.command.*;
import by.platonov.music.command.impl.LogoutCommand;
import by.platonov.music.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * initial entry point for handling all requests
 * implementation of Front Controller design pattern
 *
 * @author dzmitryplatonov on 2019-06-06.
 * @version 0.0.1
 */
@Log4j2
@WebServlet(urlPatterns = "/controller", loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
        maxFileSize = 20971520L, // 20 MB
        maxRequestSize = 20971520L // 20 MB
)
public class FrontController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        RequestContent content = new RequestContent.Builder().fromRequest(request).build();

        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(content);
        CommandResult commandResult = command.execute(content);

        commandResult.getAttributes().forEach(request::setAttribute);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);
        request.getSession().setAttribute(RequestConstant.BACKUP, commandResult);

        if (command.getClass().isAssignableFrom(LogoutCommand.class)) {
            request.getSession().invalidate(); //session need to be invalidated if command is logout
        }

        if (commandResult.getResponseType() == CommandResult.ResponseType.FORWARD) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getServletContext().getContextPath() + commandResult.getPage());
        }

    }
}
