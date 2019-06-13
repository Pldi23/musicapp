package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class LoginIsNotNullSpecification implements SqlSpecification {

    @Override
    public String toSqlClauses() {
        return "where login is not null;";
    }
}
