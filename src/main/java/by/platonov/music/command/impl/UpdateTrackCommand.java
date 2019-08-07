package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.*;
import lombok.extern.log4j.Log4j2;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to update {@link Track}
 *
 * @author Dzmitry Platonov on 2019-07-06.
 * @version 0.0.1
 */
@Log4j2
public class UpdateTrackCommand implements Command {

    private AdminService adminService;
    private CommonService commonService;

    public UpdateTrackCommand(AdminService adminService, CommonService commonService) {
        this.adminService = adminService;
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.UPDATE_TRACK_PAGE with violations if it was found
     * forward to {@link PageConstant}.UPDATE_TRACK_PAGE with result message
     * executes {@link ErrorCommand} if {@link Exception} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new TrackNameValidator(
                        new SingerValidator(
                                new AuthorValidator(
                                        new GenreValidator(
                                                new ReleaseDateValidator(null))))).apply(content);

        try {
            Track track = Track.builder()
                    .id(Long.parseLong(content.getRequestParameter(ID)[0]))
                    .uuid(content.getRequestParameter(UUID)[0])
                    .name(content.getRequestParameter(TRACKNAME)[0])
                    .singers(new HashSet<>())
                    .authors(new HashSet<>())
                    .length(getAudioLength(content.getRequestParameter(UUID)[0]))
                    .genre(commonService.getGenre(content.getRequestParameter(GENRE)[0]))
                    .releaseDate(LocalDate.parse(content.getRequestParameter(RELEASE_DATE)[0]))
                    .build();

            String[] singerNames = content.getRequestParameter(SINGER);
            for (String singerName : singerNames) {
                if (!singerName.isEmpty()) {
                    track.getSingers().add(commonService.getOrAddMusician(singerName.trim()));
                }
            }
            String[] authorNames = content.getRequestParameter(AUTHOR);
            for (String authorName : authorNames) {
                if (!authorName.isEmpty()) {
                    track.getAuthors().add(commonService.getOrAddMusician(authorName.trim()));
                }
            }

            if (violations.isEmpty()) {

                String locale = (String) content.getSessionAttribute(LOCALE);
                String result = adminService.updateTrack(track) ?
                        track.getName() + " " + MessageManager.getMessage("updated", locale) :
                        MessageManager.getMessage("failed", locale);

                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPDATE_TRACK_PAGE,
                        Map.of(UPDATE_RESULT, result, ENTITY, track));
            } else {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPDATE_TRACK_PAGE,
                        Map.of(VALIDATOR_RESULT, violations, ENTITY, track));
            }
        } catch (ServiceException | IOException | TagException | ReadOnlyFileException | CannotReadException
                | InvalidAudioFrameException e) {
            log.error("command couldn't provide track for update", e);
            return new ErrorCommand(e).execute(content);
        }
    }

    /**
     * helper method to get track length (duration)
     * @param uuid of the required track
     * @return long seconds
     */
    private long getAudioLength(String uuid) throws IOException, TagException, ReadOnlyFileException,
            CannotReadException, InvalidAudioFrameException {
        AudioFile audioFile = AudioFileIO.read(new File(
                ResourceBundle.getBundle("app").getString("app.music.uploads") + File.separator + uuid));
        return audioFile.getAudioHeader().getTrackLength();
    }
}
