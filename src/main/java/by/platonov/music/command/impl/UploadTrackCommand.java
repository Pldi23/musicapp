package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.*;
import by.platonov.music.service.FileService;
import by.platonov.music.util.HashGenerator;
import by.platonov.music.validator.*;
import lombok.extern.log4j.Log4j2;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to upload image to application file storage
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
public class UploadTrackCommand implements Command {

    private AdminService adminService;
    private CommonService commonService;
    private FileService fileService;

    public UploadTrackCommand(AdminService adminService, CommonService commonService, FileService fileService) {
        this.adminService = adminService;
        this.commonService = commonService;
        this.fileService = fileService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.UPLOAD_TRACK_PAGE with violations if it was found
     * forward to {@link PageConstant}.UPLOAD_TRACK_PAGE with result message after execution is complete
     * executes {@link ErrorCommand} if {@link Exception} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new FilePartValidator(
                        new TrackNameValidator(
                                new SingerValidator(
                                        new AuthorValidator(
                                                new ReleaseDateValidator(null))))).apply(content);

        if (violations.isEmpty()) {
            try {
                Part filePart = content.getPart(MEDIA_PATH).get();

                String trackname = content.getRequestParameter(TRACKNAME)[0].trim();
                if (!commonService.searchTrackByName(trackname).isEmpty()) {
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPLOAD_TRACK_PAGE,
                            Map.of(ADD_RESULT, trackname + " " +
                                    MessageManager.getMessage("exist", (String) content.getSessionAttribute(LOCALE))));
                }

                String genreTitle = content.getRequestParameter(GENRE)[0];
                LocalDate releaseDate = LocalDate.parse(content.getRequestParameter(RELEASE_DATE)[0]);

                Genre genre = commonService.getGenre(genreTitle);
                HashGenerator generator = new HashGenerator();
                File file = fileService.createFile(filePart, generator.generateHash());
                Track track = Track.builder()
                        .uuid(file.getName())
                        .name(trackname)
                        .length(getAudioLength(file))
                        .authors(buildMusicians(AUTHOR, content))
                        .singers(buildMusicians(SINGER, content))
                        .releaseDate(releaseDate)
                        .genre(genre)
                        .build();

                String locale = (String) content.getSessionAttribute(LOCALE);
                String result = adminService.addTrack(track) ?
                        track.getName() + " " + MessageManager.getMessage("added", locale)
                        : MessageManager.getMessage("failed", locale);

                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPLOAD_TRACK_PAGE,
                        Map.of(ADD_RESULT, result));
            } catch (ServiceException | IOException | TagException | ReadOnlyFileException | CannotReadException | InvalidAudioFrameException e) {
                log.error("couldn't provide track to next page", e);
                return new ErrorCommand(e).execute(content);
            }
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPLOAD_TRACK_PAGE,
                Map.of(VALIDATOR_RESULT, violations));
    }

    /**
     * helper method to get track length (duration)
     * @param file audio file
     * @return long seconds
     */
    private long getAudioLength(File file) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        AudioFile audioFile = AudioFileIO.read(file);
        return audioFile.getAudioHeader().getTrackLength();
    }

    private Set<Musician> buildMusicians(String requestParameter, RequestContent content) throws ServiceException {
        Set<Musician> musicians = new HashSet<>();
        String[] musicianNames = content.getRequestParameter(requestParameter);
        for (String musicianName : musicianNames) {
            if (!musicianName.isEmpty()) {
                musicians.add(commonService.getOrAddMusician(musicianName.trim()));
            }
        }
        return musicians;
    }
}

