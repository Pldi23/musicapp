package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.service.GenreService;
import by.platonov.music.service.MusicianService;
import by.platonov.music.service.TrackService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class AddTrackCommandTest {

    @Ignore
    @Test
    void execute() {
        RequestContent content = mock(RequestContent.class);
        Track track = Track.builder().id(7)
                .path(Path.of("/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/music/avicii-tim.mp3"))
                .name("TestName").singers(new HashSet<>()).authors(new HashSet<>()).genre(Genre.builder().id(1)
                        .title("pop").build()).releaseDate(LocalDate.parse("1986-02-07")).length(200).build();
//        AddTrackCommand command = new AddTrackCommand(new TrackService(), new GenreService(), new MusicianService());
        when(content.getRequestParameter(RequestConstant.MEDIA_PATH))
                .thenReturn(new String[]{"/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/music/avicii-tim.mp3"});
        when(content.getRequestParameter(RequestConstant.TRACKNAME))
                .thenReturn(new String[]{"TestName"});
        when(content.getRequestParameter(RequestConstant.GENRE)).thenReturn(new String[]{"pop"});
        when(content.getRequestParameter(RequestConstant.RELEASE_DATE)).thenReturn(new String[]{"1986-02-07"});
        when(content.getRequestParameter(RequestConstant.LENGTH)).thenReturn(new String[]{"200"});
//        CommandResult actual = command.execute(content);
        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                Map.of("addresult", track + " successfully added"));
//        assertEquals(expected, actual);
    }
}