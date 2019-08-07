package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;

import java.util.Map;

/**
 * forwards to {@link PageConstant}.PAYMENT_HISTORY_PAGE
 *
 * @author Dzmitry Platonov on 2019-08-07.
 * @version 0.0.1
 */
public class ToPaymentHistoryCommand implements Command {

    private UserService userService;

    public ToPaymentHistoryCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forwards to {@link PageConstant}.PAYMENT_HISTORY_PAGE with
     * set of payments,
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String login = content.getRequestParameter(RequestConstant.LOGIN)[0];
        try {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PAYMENT_HISTORY_PAGE,
                    Map.of(RequestConstant.ENTITIES, userService.getUserPayments(login)));
        } catch (ServiceException e) {
            return new ErrorCommand(e).execute(content);
        }
    }
}
