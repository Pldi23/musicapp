package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-22.
 * @version 0.0.1
 */
public class NotConfirmedRegistrationUserSpecification implements SqlSpecification {
    @Override
    public String toSqlClauses() {
        return "where active_status = false and verification_hash is not null and created_at <= now() - INTERVAL '1 DAY';";
    }
}
