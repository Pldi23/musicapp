package by.platonov.music.service;

import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
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

    public List<User> login(String login) throws ServiceException {
        SqlSpecification specification = new UserLoginSpecification(login);
        try {
            return repository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    public boolean register(User user) throws ServiceException {
        try {
            return repository.add(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }

    public boolean activate(String email, String hash) throws ServiceException {
        boolean result = false;
        SqlSpecification specification = new UserEmailHashSpecification(email, hash);
        List<User> users;
        try {
            users = repository.query(specification);
            if (!users.isEmpty()) {
                User user = users.get(0);
                user.setActive(true);
                user.setHash(null);
                result = repository.update(user);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
        }
        return result;
    }

    public void removeNotActiveUser() throws ServiceException {
        SqlSpecification specification = new NotConfirmedRegistrationUserSpecification();
        List<User> users;
        try {
            users = repository.query(specification);
            if (!users.isEmpty()) {
                for (User user : users) {
                    repository.remove(user);
                }
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception for user service", e);
        }
    }
}
