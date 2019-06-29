package by.platonov.music.service;

import by.platonov.music.entity.Genre;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.GenreRepository;
import by.platonov.music.repository.specification.GenreTitleSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-28.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class GenreService {

    private GenreRepository repository = GenreRepository.getInstance();

    public Genre getGenre(String genreTitle) throws ServiceException {
        Genre genre = Genre.builder().title(genreTitle).build();
        try {
            repository.add(genre);
            SqlSpecification specification = new GenreTitleSpecification(genreTitle);
            Genre result = repository.query(specification).get(0);
            log.debug("getting " + result + " from repository");
            return result;
        } catch (RepositoryException e) {
            throw new ServiceException(repository + " provide an exception to service" + e);
        }
    }
}
