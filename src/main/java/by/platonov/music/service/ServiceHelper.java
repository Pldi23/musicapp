package by.platonov.music.service;

import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.specification.SearchSpecification;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
class ServiceHelper {

    <T> List<T> search(String searchRequest, Repository<T> repository) throws ServiceException {
        try {
            return repository.query(new SearchSpecification(searchRequest));
        } catch (RepositoryException e) {
            throw new ServiceException("Musician repository provide exception for Musician service", e);
        }
    }
}
