package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.util.MusicPlayer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import lombok.extern.log4j.Log4j2;

import java.io.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-29.
 * @version 0.0.1
 */
@Log4j2
public class PlaySoundCommand implements Command {

    @Override
    public CommandResult execute(RequestContent content) {
        String path = content.getRequestParameter("tr")[0];
        MusicPlayer player = new MusicPlayer(path);
        log.debug("path " + path);
        player.run();
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SEARCH_PAGE);
    }


}
