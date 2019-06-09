package by.platonov.music.repository.specification;

import by.platonov.music.entity.User;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class SelectUserLoginSpecification implements SqlSpecification<User> {

    private String login;

    public SelectUserLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public String toSqlClauses() {
        return String.format("login = '%s'", login);
    }
}
