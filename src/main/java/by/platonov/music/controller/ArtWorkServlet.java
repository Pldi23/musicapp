package by.platonov.music.controller;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-17.
 * @version 0.0.1
 */
@WebServlet(urlPatterns = "/artwork")
public class ArtWorkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), StandardCharsets.UTF_8);
        File file = new File(ResourceBundle.getBundle("app").getString("app.music.uploads"), filename);
        System.out.println(file.toPath().toString());
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            BufferedImage image = audioFile.getTag().getFirstArtwork().getImage();
            response.setContentType("image/jpeg");
            ImageIO.write(image, "jpg", response.getOutputStream());
//            out.close();
        } catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            response.sendError(404);
        }
    }
}
