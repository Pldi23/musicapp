package by.platonov.music.controller.filter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
class XssRequestWrapperTest {

    private XssRequestWrapper wrapper;
    private HttpServletRequest request;

    @ParameterizedTest
    @ValueSource(strings = {"<script>", "<script>alert(‘XSS’)</script>", "<script>alert(document.cookie)</script>"})
    void stripXSSShouldReturnEmpty(String input) {
        request = mock(HttpServletRequest.class);
        wrapper = new XssRequestWrapper(request);
        String actual = wrapper.stripXSS(input);
        System.out.println(actual);
        assertEquals("", actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"<body onload=alert(‘something’)>;",
            "<script type=”text/javascript”>var test=’../example.php?cookie_data=’+escape(document.cookie);</script>",
             "<script>destroyWebsite();</script>",})
    void stripXSSShouldReturnChanged(String input) {
        request = mock(HttpServletRequest.class);
        wrapper = new XssRequestWrapper(request);
        String actual = wrapper.stripXSS(input);
        System.out.println(actual);
        assertNotEquals(input, actual);
    }
}