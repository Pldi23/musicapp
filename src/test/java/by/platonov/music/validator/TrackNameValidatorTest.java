package by.platonov.music.validator;

import by.platonov.music.MessageManager;
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
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
class TrackNameValidatorTest {

    public static final String EXPECTED_VIOLATION_MESSAGE =
            "Track name must contain at least one symbol and doesn't have format suffix";

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
//        Locale.setDefault(new Locale("en", "US"));
        when(content.getRequestParameters()).thenReturn(Map.of("trackname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.TRACKNAME)).thenReturn(new String[]{input});
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.trackname", "en_US")));

        assertEquals(expected, actual);
    }
}