package by.platonov.music.service;

import by.platonov.music.command.CommandResult;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.LoginPasswordSpecification;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@Log4j2
public class UserService {

    private static final UserService instance = new UserService();
    public static UserService getInstance() {
        return instance;
    }

    public CommandResult login(HttpServletRequest request) {
        log.debug("inside command login");
        CommandResult commandResult;
        Repository<User> repository = UserRepository.getInstance();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        List<User> users = new ArrayList<>();
        try {
            users = repository.query(new LoginPasswordSpecification(login, password));
        } catch (RepositoryException e) {
            log.error("Broken repository", e);
        }
        if (!users.isEmpty() && !users.get(0).isAdmin()) {
            request.setAttribute("user", users.get(0).getFirstname());
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/main.jsp");
        } else if (!users.isEmpty() && users.get(0).isAdmin()){
            request.setAttribute("adminName", users.get(0).getFirstname());
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/admin-main.jsp");
        } else {
            request.setAttribute("errorLoginPassMessage", "Incorrect login or password");
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/login.jsp");
        }
        return commandResult;
    }

    public CommandResult logout(HttpServletRequest request) {
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "index.jsp");
    }
}
