package by.platonov.music.service;

import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.entity.FilePartBean;
import by.platonov.music.repository.FilePartRepository;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.TrackRepository;
import by.platonov.music.repository.specification.*;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Path;
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

    private Repository<Track> trackRepository = TrackRepository.getInstance();
    private Repository<FilePartBean> filePartBeanRepository = FilePartRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Track> searchName(String searchRequest) throws ServiceException {
        SqlSpecification specification = new SearchNameSpecification(searchRequest);
        log.debug("searching tracks in " + trackRepository);
        return serviceHelper.search(specification, trackRepository);
    }

    public List<Track> searchPath(String uuid) throws ServiceException {
        SqlSpecification specification = new TrackUuidSpecification(uuid);
        log.debug("searching tracks in " + trackRepository);
        return serviceHelper.search(specification, trackRepository);
    }

    public List<Track> searchid(long id) throws ServiceException {
        log.debug("searching track by id in trackRepository");
        return serviceHelper.search(new TrackIdSpecification(id), trackRepository);
    }

    public boolean add(Track track) throws ServiceException {
//        SqlSpecification specification = new TrackUuidSpecification(track.getPath());
        SqlSpecification specification = new TrackNameSpecification(track.getName());
        try {
            if (trackRepository.query(specification).isEmpty()) {
                log.debug("adding " + track + " to trackRepository");
//                filePartBeanRepository.add(filePartBean);
                return trackRepository.add(track);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Repository provide an exception to service", e);
        }
        return false;
    }

    public boolean remove(String uuid) throws ServiceException {
        try {
            List<Track> tracks = trackRepository.query(new TrackUuidSpecification(uuid));
            if (!tracks.isEmpty()) {
                Track track = tracks.get(0);
                log.debug("Removing track " + track);
                return trackRepository.remove(track);
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
