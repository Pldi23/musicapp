package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
public class SelectIdSpecification implements SqlSpecification {

    private long id;
    private String table;

    public SelectIdSpecification(long id, String table) {
        this.id = id;
        this.table = table;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where %s.id = %d", table, id);
    }
}
