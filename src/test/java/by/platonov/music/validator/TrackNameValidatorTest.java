package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
class TrackNameValidatorTest {

    private TrackNameValidator validator = new TrackNameValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"name", "n", "имя", "i never feel so good"})
    void applyPositive(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of("trackname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.TRACKNAME)).thenReturn(new String[]{input});
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "name.mp3", "name.audio"})
    void applyNegative(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of("trackname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.TRACKNAME)).thenReturn(new String[]{input});
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.trackname", "ru_RU")));

        assertEquals(expected, actual);
    }
}