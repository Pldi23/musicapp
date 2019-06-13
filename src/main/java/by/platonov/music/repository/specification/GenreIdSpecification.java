package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class GenreIdSpecification implements SqlSpecification {

    private long id;

    public GenreIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where genre.id = %d", id);
    }
}
