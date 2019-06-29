package by.platonov.music.service;

import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.TrackRepository;
import by.platonov.music.repository.specification.SqlSpecification;
import by.platonov.music.repository.specification.TrackPathSpecification;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class TrackService {

    private Repository<Track> repository = TrackRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Track> search(String searchRequest) throws ServiceException {
        log.debug("searching tracks in " + repository);
        return serviceHelper.search(searchRequest, repository);
    }

    public boolean add(Track track) throws ServiceException {
        SqlSpecification specification = new TrackPathSpecification(track.getPath());
        try {
            if (repository.query(specification).isEmpty()) {
                log.debug("adding " + track + " to repository");
                return repository.add(track);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception to service", e);
        }
        return false;
    }


}
