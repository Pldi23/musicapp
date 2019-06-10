package by.platonov.music.repository.specification;

import by.platonov.music.entity.User;

/**
 * @author dzmitryplatonov on 2019-06-08.
 * @version 0.0.1
 */
public class SelectLoginIsNotNullSpecification implements Specification<User>, SqlSpecification {
    @Override
    public boolean specify(User user) {
        return true;
    }

    @Override
    public String toSqlClauses() {
        return "login is not null;";
    }
}