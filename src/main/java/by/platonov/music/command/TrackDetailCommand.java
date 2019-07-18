package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
@Log4j2
public class TrackDetailCommand implements Command {

    private CommonService commonService;

    public TrackDetailCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String trackId = content.getRequestParameter(RequestConstant.ID)[0];
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        Track track;
        List<Playlist> playlists;
        try {
            track = commonService.searchTrackById(trackId);
//            AudioFile f = AudioFileIO.read(new File(ResourceBundle.getBundle("app").getString("app.music.uploads") +
//                    File.separator + track.getUuid()));
//            Tag tag = f.getTag();
//            if (tag.hasField("Cover Art")) {
//                byte[] b = tag.getFirstArtwork().getBinaryData();
//            }

            playlists = user != null ? commonService.searchPlaylistsByTrackAndUser(Long.parseLong(trackId), user)
                    : new ArrayList<>();

        } catch (ServiceException e) {
            log.error("command couldn't provide track", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_PAGE,
                Map.of(RequestConstant.TRACK, track, RequestConstant.PLAYLISTS_ATTRIBUTE, playlists));
    }
}
