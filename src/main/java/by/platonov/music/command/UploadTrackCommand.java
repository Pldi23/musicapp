package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.*;
import by.platonov.music.util.UnicNameGenerator;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
public class UploadTrackCommand implements Command {

    private AdminService adminService;
    private CommonService commonService;

    public UploadTrackCommand(AdminService adminService, CommonService commonService) {
        this.adminService = adminService;
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new FilePartValidator(
                        new TrackNameValidator(
                                new SingerValidator(
                                        new ReleaseDateValidator(null)))).apply(content);

        if (violations.isEmpty()) {
            try {
                Part filePart = content.getPart(MEDIA_PATH).get();

                String trackname = content.getRequestParameter(TRACKNAME)[0].trim();
                if (!commonService.searchTrackByName(trackname).isEmpty()) {
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPLOAD_TRACK_PAGE,
                            Map.of(ADD_RESULT, trackname + " " + MessageManager.getMessage("exist", (String) content.getSessionAttribute(LOCALE))));
                }

                String genreTitle = content.getRequestParameter(GENRE)[0];
                LocalDate releaseDate = LocalDate.parse(content.getRequestParameter(RELEASE_DATE)[0]);

                Set<Musician> singers = new HashSet<>();
                Set<Musician> authors = new HashSet<>();

                String[] singerNames = content.getRequestParameter(SINGER);
                for (String singerName : singerNames) {
                    if (!singerName.isEmpty()) {
                        singers.add(commonService.getMusician(singerName.trim()));
                    }
                }
                String[] authorNames = content.getRequestParameter(AUTHOR);
                for (String authorName : authorNames) {
                    if (!authorName.isEmpty()) {
                        authors.add(commonService.getMusician(authorName.trim()));
                    }
                }

                Genre genre = commonService.getGenre(genreTitle);
                File file = createFile(filePart, UnicNameGenerator.generateUnicName());
                Track track = Track.builder()
                        .uuid(file.getName())
                        .name(trackname)
                        .length(getAudioLength(file))
                        .authors(authors)
                        .singers(singers)
                        .releaseDate(releaseDate)
                        .genre(genre)
                        .build();

                adminService.addTrack(track);

                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPLOAD_TRACK_PAGE,
                        Map.of(ADD_RESULT, track.getName() + " " + MessageManager.getMessage("added", (String) content.getSessionAttribute(LOCALE))));
            } catch (ServiceException | IOException | TagException | ReadOnlyFileException | CannotReadException | InvalidAudioFrameException e) {
                log.error("couldn't provide track to next page", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPLOAD_TRACK_PAGE,
                Map.of(VALIDATOR_RESULT, violations));
    }

    private File createFile(Part part, String uuid) throws IOException {
        String filename = Path.of(part.getSubmittedFileName()).getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf('.'));
        File file = new File(ResourceBundle.getBundle("app").getString("app.music.uploads"), uuid + extension);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }

    private long getAudioLength(File file) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        AudioFile audioFile = AudioFileIO.read(file);
        return audioFile.getAudioHeader().getTrackLength();
    }
}

