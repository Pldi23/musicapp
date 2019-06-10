package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
public class SelectIdIsNotNullSpecification implements SqlSpecification {
    @Override
    public String toSqlClauses() {
        return "id IS NOT NULL;";
    }
}
