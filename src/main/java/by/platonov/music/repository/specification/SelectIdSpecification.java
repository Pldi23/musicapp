package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
public class SelectIdSpecification implements SqlSpecification {

    private long id;

    public SelectIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format("id = %d", id);
    }
}
