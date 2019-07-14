package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
class PlaylistNameValidatorTest {

    private PlaylistNameValidator validator = new PlaylistNameValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"название", "Dimaздфн", "dimaPlaylist", "Плэлист дима", "ДИМА Плый лист", "ДИ МА", "чу плэйлист", "Ди-ма",
            "fdulttpbrkvyjghcneawxioqwertyu"})
    void applyPositive(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of("name", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.NAME)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"C", "chu.", "D&MA", "пла#тонов", "пла3тонов", "fdulttpbrkvyjghcneawxioqwertyuiop111111222222888888777744",
            "чу?", " Дима", "Дима ", "Дима-", "-Дима", "", " ", "1234"})
    void applyNegative(String input) {
        String message = "Playlist name must contain at least one and at most 50 symbols";
        Locale.setDefault(new Locale("en_US"));
        when(content.getRequestParameters()).thenReturn(Map.of("name", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.NAME)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(message));

        assertEquals(expected, actual);
    }
}