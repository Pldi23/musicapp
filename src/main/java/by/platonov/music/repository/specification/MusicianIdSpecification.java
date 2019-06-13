package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-13.
 * @version 0.0.1
 */
public class MusicianIdSpecification implements SqlSpecification {

    private long id;

    public MusicianIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where musician.id = %d", id);
    }
}
