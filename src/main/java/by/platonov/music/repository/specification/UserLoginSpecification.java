package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class UserLoginSpecification implements SqlSpecification {

    private String login;

    public UserLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where login = '%s'", login);
    }
}
