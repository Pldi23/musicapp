package by.platonov.music.service;

import by.platonov.music.command.CommandResult;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.LoginPasswordSpecification;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@Log4j2
public class CommonService {

    private static final CommonService instance = new CommonService();
    public static CommonService getInstance() {
        return instance;
    }

    public CommandResult login(HttpServletRequest request) {
        CommandResult commandResult;
        Repository<User> repository = UserRepository.getInstance();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        List<User> users;
        try {
            users = repository.query(new LoginPasswordSpecification(login, password));
        } catch (RepositoryException e) {
            log.error("Broken repository", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "error.jsp");
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
        request.getSession().invalidate();
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/index.jsp");
    }

    public CommandResult register(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String birthdate = request.getParameter("birthdate");
        String email = request.getParameter("e-mail");
        String gender = request.getParameter("gender");
        log.debug(login);
        log.debug(password);
        log.debug(firstname);
        log.debug(lastname);
        log.debug(birthdate);
        log.debug(email);
        log.debug(gender);

        User user = User.builder()
                .login(login)
                .password(password)
                .admin(false)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .birthDate(LocalDate.parse(birthdate))
                .gender(Gender.valueOf(gender.toUpperCase()))
                .playlists(new HashSet<>())
                .build();
        Repository<User> repository = UserRepository.getInstance();
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            log.error("Broken repository", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, "error.jsp");
        }
        request.setAttribute("user", firstname);
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/main.jsp");
    }

    public CommandResult toRegistr(HttpServletRequest request) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/registration.jsp");
    }
}
