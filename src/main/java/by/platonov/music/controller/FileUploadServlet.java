package by.platonov.music.controller;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.UploadCommand;
import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.FilePartService;
import by.platonov.music.service.GenreService;
import by.platonov.music.service.MusicianService;
import by.platonov.music.service.TrackService;
import lombok.extern.log4j.Log4j2;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
@Deprecated
@Log4j2
@WebServlet(name = "uploadServlet", urlPatterns = {"/upload"}, loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
        maxFileSize = 15485760L, // 10 MB
        maxRequestSize = 20971520L // 20 MB
)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent content = RequestContent.createWithAttributes(request);
        Command command = new UploadCommand(new MusicianService(), new GenreService(), new TrackService(), FilePartService.getInstance());
        CommandResult commandResult = command.execute(content);

        commandResult.getAttributes().forEach(request::setAttribute);
        commandResult.getSessionAttributes().forEach(request.getSession()::setAttribute);

        if (commandResult.getResponseType() == CommandResult.ResponseType.FORWARD) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(commandResult.getPage());
        }

//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8");
//        Properties properties = new Properties();
//        properties.load(FileUploadServlet.class.getResourceAsStream("/app.properties"));
//
//        String uploadFilePath = properties.getProperty("app.music.uploads");
//
//        File uploadFolder = new File(uploadFilePath);
//        if (!uploadFolder.exists()) {
//            uploadFolder.mkdirs();
//        }
//
//        Part filePart = request.getPart(MEDIA_PATH);
//        String filename = filePart.getSubmittedFileName();
//
//        if (!filePart.getContentType().equalsIgnoreCase("audio/mp3")) {
//            log.debug(filePart.getContentType());
//            request.setAttribute(ADD_RESULT_ATTRIBUTE, CommandMessage.INVALID_FORMAT_MESSAGE);
//            request.getRequestDispatcher(PageConstant.ADMIN_PAGE).forward(request, response);
//            return;
//        }
//
//        String destination = uploadFolder + File.separator + filename;
//        log.debug("writing to " + destination);
//        filePart.write(destination);
//
//        String trackname = request.getParameter(TRACKNAME);
//        String genreTitle = request.getParameter(GENRE);
//        log.debug(genreTitle);
//        LocalDate releaseDate = LocalDate.parse(request.getParameter(RELEASE_DATE));
//        log.debug(releaseDate);
//        long length = 0;
//        try {
//            AudioFile audioFile = AudioFileIO.read(new File(destination));
//            length = audioFile.getAudioHeader().getTrackLength();
//        } catch (CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | TagException e) {
//            log.error("couldn't handle audio file " + filename, e);
//        }
//        log.debug(length);
//
//        MusicianService musicianService = new MusicianService();
//        GenreService genreService = new GenreService();
//        TrackService trackService = new TrackService();
//        Set<Musician> singers = new HashSet<>();
//        Set<Musician> authors = new HashSet<>();
//        try {
//            Map<String, String[]> parameterMap = request.getParameterMap();
//                String[] singerNames = parameterMap.get(SINGER);
//                for (String singerName : singerNames) {
//                    if (!singerName.isEmpty()) {
//                        singers.add(musicianService.getMusician(singerName));
//                    }
//                }
//                String[] authorNames = parameterMap.get(AUTHOR);
//                for (String authorName : authorNames) {
//                    if (!authorName.isEmpty()) {
//                        authors.add(musicianService.getMusician(authorName));
//                    }
//                }
//
//            Genre genre = genreService.getGenre(genreTitle);
//            Track track = Track.builder()
//                    .path(Path.of(destination))
//                    .name(trackname)
//                    .authors(authors)
//                    .singers(singers)
//                    .length(length)
//                    .releaseDate(releaseDate)
//                    .genre(genre)
//                    .build();
//
//            String message = trackService.add(track) ? track + CommandMessage.SUCCESSFULLY_ADDED_MESSAGE :
//                    track + CommandMessage.ALREADY_EXIST_MESSAGE;
//            request.setAttribute(ADD_RESULT_ATTRIBUTE, message);
//            request.getRequestDispatcher(PageConstant.ADMIN_PAGE).forward(request, response);
//        } catch (ServiceException e) {
//            log.error("Exception during admin adding track to db", e);
//            response.sendError(404);
//        }
//    }
    }
}
