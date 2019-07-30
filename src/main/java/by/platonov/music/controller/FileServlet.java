package by.platonov.music.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ResourceBundle;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@WebServlet(value = "/music/*")
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), StandardCharsets.UTF_8);
        File file = new File(ResourceBundle.getBundle("app").getString("app.music.uploads"), filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=" + file.getName() + "");
        response.setHeader("Accept-Ranges", "bytes");
        Files.copy(file.toPath(), response.getOutputStream());
    }

}
