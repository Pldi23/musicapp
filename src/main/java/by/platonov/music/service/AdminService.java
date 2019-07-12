package by.platonov.music.service;

import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.TrackRepository;
import by.platonov.music.repository.specification.*;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
public class AdminService {

    private final Repository<Track> trackRepository = TrackRepository.getInstance();


    public boolean addTrack(Track track) throws ServiceException {
        SqlSpecification specification = new TrackNameSpecification(track.getName());
        try {
            if (trackRepository.query(specification).isEmpty()) {
                log.debug("adding " + track + " to trackRepository");
                return trackRepository.add(track);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception to service", e);
        }
        return false;
    }

    public boolean removeTrack(Track track) throws ServiceException {
        try {
            log.debug("Removing track " + track);
            return trackRepository.remove(track);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public boolean updateTrack(Track track) throws ServiceException {
        try {
            return trackRepository.update(track);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

}
