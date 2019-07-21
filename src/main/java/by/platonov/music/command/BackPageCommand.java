package by.platonov.music.command;

import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class BackPageCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD,
                content.getReferer(), Map.of(RequestConstant.OFFSET, 0L),
                Map.of(RequestConstant.PREVIOUS_OFFSET, 0L, RequestConstant.NEXT_OFFSET, 0L));
    }
}
