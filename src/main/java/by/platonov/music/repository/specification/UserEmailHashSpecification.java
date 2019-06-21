package by.platonov.music.repository.specification;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
public class UserEmailHashSpecification implements SqlSpecification {

    private String email;
    private String hash;

    public UserEmailHashSpecification(String email, String hash) {
        this.email = email;
        this.hash = hash;
    }

    @Override
    public String toSqlClauses() {
        return String.format("where e_mail = '%s' and verification_hash = '%s';", email, hash);
    }
}
