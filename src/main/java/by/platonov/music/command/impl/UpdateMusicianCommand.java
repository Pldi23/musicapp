package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
import by.platonov.music.validator.MusicianValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Set;

/**
 * to update {@link Musician}
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class UpdateMusicianCommand implements Command {

    private AdminService adminService;

    public UpdateMusicianCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.UPDATE_MUSICIAN_PAGE with violations if it was found
     * forward to {@link PageConstant}.UPDATE_MUSICIAN_PAGE with result message
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        try {
            Set<Violation> violations = new MusicianValidator(null).apply(content);
            String musicianId = content.getRequestParameter(RequestConstant.ID)[0];
            String musicianName = content.getRequestParameter(RequestConstant.MUSICIAN)[0];
            Musician musician = Musician.builder().id(Long.parseLong(musicianId)).name(musicianName).build();

            if (violations.isEmpty()) {
                String locale = (String) content.getSessionAttribute(RequestConstant.LOCALE);
                String result = adminService.updateMusician(musician) ?
                        MessageManager.getMessage("updated", locale) : MessageManager.getMessage("failed", locale);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPDATE_MUSICIAN_PAGE,
                        Map.of(RequestConstant.PROCESS, result, RequestConstant.ENTITY, musician));
            } else {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPDATE_MUSICIAN_PAGE,
                        Map.of(RequestConstant.VALIDATOR_RESULT, violations, RequestConstant.ENTITY, musician));
            }
        } catch (ServiceException e) {
            log.error("command couldn't update musician", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
