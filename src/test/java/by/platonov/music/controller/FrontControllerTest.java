package by.platonov.music.controller;

import by.platonov.music.command.CommandFactory;
import by.platonov.music.command.RequestContent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
class FrontControllerTest extends Mockito {

    @Test
    void testProcessRequest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Enumeration<String> requestAttributeNames = Collections.enumeration(List.of());
        Enumeration<String> sessionAttributeNames = Collections.enumeration(List.of());

        HttpSession session = mock(HttpSession.class);
//        RequestContent content = mock(RequestContent.class);

//        when(request.getParameter("command")).thenReturn("login");
//        when(request.getParameter("login")).thenReturn("pldi");
//        when(request.getParameter("password")).thenReturn("qwerty");
        when(request.getAttributeNames()).thenReturn(requestAttributeNames);
        when(request.getSession()).thenReturn(session);
        when(session.getAttributeNames()).thenReturn(sessionAttributeNames);
        when(request.getParameterMap()).thenReturn(Map.of("command", new String[] {"login"}));

//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);

        new FrontController().doPost(request, response);

        verify(request, atLeast(1)).getParameter("login"); // only if you want to verify username was called...
//        writer.flush(); // it may not have been flushed yet...
//        assertTrue(stringWriter.toString().contains("Admin Dima, Hello"));
    }
}