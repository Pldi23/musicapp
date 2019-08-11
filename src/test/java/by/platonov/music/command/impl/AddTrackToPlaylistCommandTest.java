package by.platonov.music.command.impl;

import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.message.MessageManager;
import by.platonov.music.service.CommonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static by.platonov.music.constant.RequestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
class AddTrackToPlaylistCommandTest {

    private RequestContent content;
    private CommonService commonService;
    private AddTrackToPlaylistCommand command;

    @BeforeEach
    void setUp() {
        commonService = mock(CommonService.class);
        Map<String, String[]> params = new HashMap<>();
        params.put(RequestConstant.PLAYLIST_ID, new String[]{"1"});
        Map<String, Object> sessionAttrs = new HashMap<>();
        sessionAttrs.put(RequestConstant.LOCALE, "ru_RU");
        sessionAttrs.put(BACKUP, new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LIBRARY_PAGE));
        content = new RequestContent.Builder()
                .withRequestParameters(params)
                .withSessionAttributes(sessionAttrs)
                .build();
        command = new AddTrackToPlaylistCommand(commonService);
    }

    @AfterEach
    void tearDown() {
        content = null;
        command = null;
        commonService = null;
    }

    @Test
    void executeTrackNotFound() {
        content.getRequestParameters().put(ID, new String[]{"10"});

        CommandResult actual = command.execute(content);

        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                Map.of(RequestConstant.PROCESS,
                        MessageManager.getMessage("message.entity.not.available",
                                (String) content.getSessionAttribute(RequestConstant.LOCALE))));

        assertEquals(expected, actual);
    }

    @Test
    void executeTrackAdded() throws ServiceException {
        content.getRequestParameters().put(ID, new String[]{"1"});
        Track track = Track.builder()
                .id(1)
                .name("Tim")
                .genre(Genre.builder().id(1).title("pop").build())
                .releaseDate(LocalDate.of(2019, 1,1))
                .length(180)
                .uuid("/users/dzmitryplatonov/Dropbox/music/avicii-tim.mp3")
                .authors(new HashSet<>())
                .singers(new HashSet<>())
                .createDate(LocalDate.now())
                .build();
        when(commonService.searchTrackById("1")).thenReturn(List.of(track));
        when(commonService.addTrackToPLaylist("1", "1")).thenReturn(true);

        CommandResult actual = command.execute(content);


        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LIBRARY_PAGE,
                Map.of(PROCESS, track.getName() + " " + MessageManager
                        .getMessage("added",
                        (String) content.getSessionAttribute(RequestConstant.LOCALE)),
                        TRACK, track));

        assertEquals(expected, actual);
    }

    @Test
    void executeException() throws ServiceException {
        content.getRequestParameters().put(ID, new String[]{"1"});
        ServiceException exception = new ServiceException();
        when(commonService.searchTrackById("1")).thenThrow(exception);

        CommandResult actual = command.execute(content);
        CommandResult expected = new ErrorCommand(exception).execute(content);

        assertEquals(expected, actual);
    }
}