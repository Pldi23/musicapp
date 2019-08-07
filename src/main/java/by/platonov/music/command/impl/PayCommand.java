package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Payment;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.message.MessageManager;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.CardNumberValidator;
import by.platonov.music.validator.CvCodeValidator;
import by.platonov.music.validator.ExpirationDateValidator;
import by.platonov.music.validator.Violation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to pay for subscription
 *
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
public class PayCommand implements Command {

    private UserService userService;

    public PayCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.PAYMENT_PAGE with violations if it was found
     * forward to {@link PageConstant}.PAYMENT_PAGE if command failed
     * redirect to {@link PageConstant}.PAYMENT_SUCCESSFUL_PAGE if execution successful
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new CardNumberValidator(
                        new ExpirationDateValidator(
                                new CvCodeValidator(null))).apply(content);

        if (violations.isEmpty()) {
            String card = content.getRequestParameter(CARD)[0];
            String amount = content.getRequestParameter(AMOUNT)[0];
            User user = (User) content.getSessionAttribute(USER);
            Payment payment = Payment.builder()
                    .amount(new BigDecimal(amount))
                    .cardNumber(card)
                    .dateTime(LocalDateTime.now())
                    .payerName(user.getLogin())
                    .build();
            try {
                String locale = (String) content.getSessionAttribute(LOCALE);
                return userService.processPayment(payment) ?
                        new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.PAYMENT_SUCCESSFUL_PAGE,
                                Map.of(), Map.of(USER, userService.searchUserByLogin(user.getLogin()).get(0))) :
                        new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PAYMENT_PAGE,
                                Map.of(PROCESS, MessageManager.getMessage("failed", locale)));

            } catch (ServiceException e) {
                return new ErrorCommand(e).execute(content);
            }
        } else {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PAYMENT_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
