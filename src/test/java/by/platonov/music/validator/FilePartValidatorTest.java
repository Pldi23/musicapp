package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-01.
 * @version 0.0.1
 */
class FilePartValidatorTest {

    private FilePartValidator validator = new FilePartValidator(null);
    private RequestContent content = mock(RequestContent.class);
    private Part part = mock(Part.class);

    @ParameterizedTest
    @ValueSource(strings = {"/Users/dzmitryplatonov/Downloads/katy-perry-feat.-nicki-minaj-swish-swish.mp3"})
    void applyPositive(String input) {
        when(content.getRequestParts()).thenReturn(List.of(part));
        when(content.getPart(RequestConstant.MEDIA_PATH)).thenReturn(Optional.of(part));
        when(content.getPart(RequestConstant.MEDIA_PATH).get().getContentType()).thenReturn("audio/mp3");
        when(part.getName()).thenReturn(input);
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }
}