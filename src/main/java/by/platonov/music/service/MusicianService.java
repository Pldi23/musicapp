package by.platonov.music.service;

import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.MusicianRepository;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.specification.MusicianNameSpecification;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@Log4j2
public class MusicianService {

    private Repository<Musician> repository = MusicianRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Musician> search(String searchRequest) throws ServiceException {
        log.debug("searching musicians in repository");
        return serviceHelper.search(searchRequest, repository);
    }

    public Musician getMusician(String musicianName) throws ServiceException {
        Musician resultMusician;
        Musician musician = Musician.builder().name(musicianName).build();
        SqlSpecification specification = new MusicianNameSpecification(musicianName);
        List<Musician> musicians;
        try {
            musicians = repository.query(specification);
            if (musicians.isEmpty()) {
                log.debug("adding " + musicianName + " to repository");
                repository.add(musician);
                log.debug("getting " + musicianName + " from repository");
                resultMusician = repository.query(specification).get(0);
            } else {
                log.debug("getting " + musicianName + " from repository");
                resultMusician = musicians.get(0);
            }
            return resultMusician;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
