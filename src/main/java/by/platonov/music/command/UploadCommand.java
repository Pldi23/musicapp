package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.FilePartBeanException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.entity.FilePartBean;
import by.platonov.music.service.FilePartService;
import by.platonov.music.service.GenreService;
import by.platonov.music.service.MusicianService;
import by.platonov.music.service.TrackService;
import by.platonov.music.validator.*;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
@Log4j2
public class UploadCommand implements Command {

    private MusicianService musicianService;
    private GenreService genreService;
    private TrackService trackService;
    private FilePartService filePartService;

    public UploadCommand(MusicianService musicianService, GenreService genreService, TrackService trackService,
                         FilePartService filePartService) {
        this.musicianService = musicianService;
        this.genreService = genreService;
        this.trackService = trackService;
        this.filePartService = filePartService;
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
                FilePartBean filePartBean = new FilePartBean(filePart);

                String trackname = content.getRequestParameter(TRACKNAME)[0];
                String genreTitle = content.getRequestParameter(GENRE)[0];
                LocalDate releaseDate = LocalDate.parse(content.getRequestParameter(RELEASE_DATE)[0]);

                Set<Musician> singers = new HashSet<>();
                Set<Musician> authors = new HashSet<>();

                String[] singerNames = content.getRequestParameter(SINGER);
                for (String singerName : singerNames) {
                    if (!singerName.isEmpty()) {
                        singers.add(musicianService.getMusician(singerName));
                    }
                }
                String[] authorNames = content.getRequestParameter(AUTHOR);
                for (String authorName : authorNames) {
                    if (!authorName.isEmpty()) {
                        authors.add(musicianService.getMusician(authorName));
                    }
                }

                Genre genre = genreService.getGenre(genreTitle);
                Track track = Track.builder()
//                        .path(filePartService.getFilePartBeanRepositoryPath(filePartBean))
                        .path(Path.of(filePartBean.getFilePartName()))
                        .name(trackname)
                        .authors(authors)
                        .singers(singers)
//                        .length(length)
                        .releaseDate(releaseDate)
                        .genre(genre)
                        .build();

                String message = trackService.add(track) ? track + CommandMessage.SUCCESSFULLY_ADDED_MESSAGE :
                        track + CommandMessage.ALREADY_EXIST_MESSAGE;

                filePartService.addFilePart(filePartBean);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                        Map.of(ADD_RESULT_ATTRIBUTE, message));
            } catch (ServiceException | FilePartBeanException e) {
                log.error("couldn't handle audio file ", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                Map.of("violations", violations));
    }

//    private long getAudioLength(Part part) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
//        File temp = Files.createTempFile("tempjavafile", ".mp3").toFile();
//        Files.copy(part.getInputStream(), temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
//        AudioFile audioFile = AudioFileIO.read(temp);
//        long length = audioFile.getAudioHeader().getTrackLength();
//        temp.delete();
//        return length;
//    }
}

