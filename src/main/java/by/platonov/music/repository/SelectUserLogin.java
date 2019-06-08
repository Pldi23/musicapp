package by.platonov.music.repository;

import by.platonov.music.entity.User;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class SelectUserLogin implements SqlSpecification<User> {

    private String login;

    public SelectUserLogin(String login) {
        this.login = login;
    }

    @Override
    public String toSqlClauses() {
        return String.format("login = '%s'", login);
    }
}
