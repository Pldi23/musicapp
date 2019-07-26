package by.platonov.music.service;

import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.*;
import by.platonov.music.repository.specification.*;
import lombok.extern.log4j.Log4j2;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
public class AdminService {

    public boolean addTrack(Track track) throws ServiceException {
        TrackRepository trackRepository = TrackRepository.getInstance();
        SqlSpecification specification = new EntityNameSpecification(track.getName());
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
        return remove(track, TrackRepository.getInstance());
    }

    public boolean updateTrack(Track track) throws ServiceException {
        return update(track, TrackRepository.getInstance());
    }

    public boolean updateMusician(Musician musician) throws ServiceException {
        return update(musician, MusicianRepository.getInstance());
    }

    public boolean updatePlaylist(Playlist playlist) throws ServiceException {
        return update(playlist, PlaylistRepository.getInstance());
    }

    public boolean removePlaylist(Playlist playlist) throws ServiceException {
        return remove(playlist, PlaylistRepository.getInstance());
    }

    public boolean removeMusician(Musician musician) throws ServiceException {
        return remove(musician, MusicianRepository.getInstance());
    }

    public boolean removeGenre(Genre genre) throws ServiceException {
        return remove(genre, GenreRepository.getInstance());
    }

    private <T> boolean remove(T t, Repository<T> repository) throws ServiceException {
        try {
            log.debug("Removing entity " + t);
            return repository.remove(t);
        } catch (RepositoryException e) {
            throw new ServiceException("repository provide exception for Admin service", e);
        }
    }

    private <T> boolean update(T t, Repository<T> repository) throws ServiceException {
        try {
            log.debug("Updating entity " + t);
            return repository.update(t);
        } catch (RepositoryException e) {
            throw new ServiceException("could't update " + t, e);
        }
    }

}
