package by.platonov.music.service;

import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.UserRepository;
import by.platonov.music.repository.specification.NotConfirmedRegistrationUserSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import by.platonov.music.repository.specification.UserEmailHashSpecification;
import by.platonov.music.repository.specification.UserLoginSpecification;

import java.util.List;

/**
 * @author dzmitryplatonov on 2019-06-17.
 * @version 0.0.1
 */
public class UserService {

    private UserRepository repository = UserRepository.getInstance();

    public List<User> login(String login) throws RepositoryException {
        SqlSpecification specification = new UserLoginSpecification(login);
        return repository.query(specification);
    }

    public boolean register(User user) throws RepositoryException {
        return repository.add(user);
    }

    public boolean activate(String email, String hash) throws RepositoryException {
        boolean result = false;
        SqlSpecification specification = new UserEmailHashSpecification(email, hash);
        List<User> users = repository.query(specification);
        if (!users.isEmpty()) {
            User user = users.get(0);
            user.setActive(true);
            user.setHash(null);
            result = repository.update(user);
        }
        return result;
    }

    public void removeNotActiveUser() throws RepositoryException {
        SqlSpecification specification = new NotConfirmedRegistrationUserSpecification();
        List<User> users = repository.query(specification);
        if (!users.isEmpty()) {
            for (User user : users) {
                repository.remove(user);
            }
        }
    }
}