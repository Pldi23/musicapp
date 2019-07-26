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
class ReleaseDateValidatorTest {

//    private static final String EXPECTED_MESSAGE = "Release date could not be in future";


    private ReleaseDateValidator validator = new ReleaseDateValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"1986-02-07", "2013-06-15"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.RELEASE_DATE, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.RELEASE_DATE)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2020-07-02", "2030-08-30"})
    void applyNegative(String input) {
//        Locale.setDefault(new Locale("en_US"));
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.RELEASE_DATE, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.RELEASE_DATE)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.futurerelease", "en_US")));

        assertEquals(expected, actual);
    }
}