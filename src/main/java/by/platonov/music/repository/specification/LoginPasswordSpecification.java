package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-16.
 * @version 0.0.1
 */
public class LoginPasswordSpecification implements SqlSpecification {

    private String login;
    private String password;

    public LoginPasswordSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where login = '%s' and password = '%s'", login, password);
    }
}
