package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.message.MessageManager;
import org.junit.jupiter.api.Test;
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
 * @author Dzmitry Platonov on 2019-08-06.
 * @version 0.0.1
 */
class CvCodeValidatorTest {

    private CvCodeValidator validator = new CvCodeValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"123", "007"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.CV_CODE, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.CV_CODE)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678901234567", "012345678910111", "", "1"})
    void applyNegative(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.CV_CODE, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.CV_CODE)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.cvcode", "ru_RU")));

        assertEquals(expected, actual);
    }
}