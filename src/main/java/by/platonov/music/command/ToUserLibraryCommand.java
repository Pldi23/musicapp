package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.service.AdminService;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
public class ToUserLibraryCommand implements Command {

    private AdminService adminService;

    public ToUserLibraryCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_LIBRARY_PAGE);
    }
}
