package by.platonov.music.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class RequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    // конструкторы
// метод извлечения информации из запроса
    public void extractValues(HttpServletRequest request) {

// реализация
    }

    // метод добавления в запрос данных для передачи в jsp
    public void insertAttributes(HttpServletRequest request) {
// реализация
    }
// some methods

}
