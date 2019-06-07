package by.platonov.music.repository;

import by.platonov.music.entity.User;

import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-07.
 * @version 0.0.1
 */
public class UserRepository implements Repository<User> {

    @Override
    public boolean add(User entity) {

        return false;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public List<User> query(Specification<User> specification) {
        return null;
    }
}
