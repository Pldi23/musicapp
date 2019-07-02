package by.platonov.music.command;

import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-29.
 * @version 0.0.1
 */
class PlaySoundCommandTest {

    @Test
    void execute() {
        PlaySoundCommand command = new PlaySoundCommand();
        RequestContent content = mock(RequestContent.class);
        String path = "/usr/local/Cellar/tomcat/9.0.20/libexec/webapps/music-app/uploads/06_-love-is-madness.mp3";
//        Track track = Track.builder().id(23).name("shape of you").genre(Genre.builder().id(1).title("pop").build())
//                .length(233).releaseDate(LocalDate.of(2017, 10, 10))
//                .path(Path.of("/usr/local/Cellar/tomcat/9.0.20/libexec/webapps/music-app/uploads/06_-love-is-madness.mp3"))
//                .singers(Set.of(Musician.builder().id(8).name("edd sheran").build()))
//                .authors(Set.of())
//                .build();
        when(content.getRequestParameter("tr")).thenReturn(new String[]{path});

        command.execute(content);
    }
}