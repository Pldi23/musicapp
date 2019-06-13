package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
public class IdIsNotNullSpecification implements SqlSpecification {
    @Override
    public String toSqlClauses() {
        return "where id IS NOT NULL;";
    }
}
