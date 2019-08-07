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

import java.util.List;

/**
 * encapsulates admin logic to provide tracks data to command layer
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
public class AdminService {

    /**
     * to add {@link Track} to {@link TrackRepository}
     * @param track to add
     * @return true if track was successfully added, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
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

    /**
     * to remove {@link Track} from {@link TrackRepository}
     * @param track to remove
     * @return true if track was successfully removed, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean removeTrack(Track track) throws ServiceException {
        log.debug("Removing track " + track);
        return remove(track, TrackRepository.getInstance());
    }

    /**
     * to update {@link Track} data
     * @param track to update
     * @return true if track was successfully updated, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean updateTrack(Track track) throws ServiceException {
        log.debug("updating track " + track);
        return update(track, TrackRepository.getInstance());
    }

    /**
     * to update {@link Musician} data in {@link MusicianRepository}
     * @param musician to update
     * @return true if musician was successfully updated, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean updateMusician(Musician musician) throws ServiceException {
        log.debug("updating musician " + musician);
        return update(musician, MusicianRepository.getInstance());
    }

    /**
     * to update {@link Playlist} data in {@link PlaylistRepository}
     * @param playlist to update
     * @return true if playlist was successfully updated, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean updatePlaylist(Playlist playlist) throws ServiceException {
        log.debug("updating playlist " + playlist);
        return update(playlist, PlaylistRepository.getInstance());
    }

    /**
     * to remove {@link Playlist} from {@link PlaylistRepository}
     * @param playlist to remove
     * @return true if playlist was successfully removed, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean removePlaylist(Playlist playlist) throws ServiceException {
        log.debug("Removing playlist " + playlist);
        return remove(playlist, PlaylistRepository.getInstance());
    }

    /**
     * to remove {@link Musician} from {@link MusicianRepository}
     * @param musician to remove
     * @return true if musician was successfully removed, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean removeMusician(Musician musician) throws ServiceException {
        log.debug("Removing musician " + musician);
        return remove(musician, MusicianRepository.getInstance());
    }

    /**
     * to remove {@link Genre} from {@link GenreRepository}
     * @param genre to remove
     * @return true if genre was successfully removed, and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public boolean removeGenre(Genre genre) throws ServiceException {
        log.debug("Removing genre " + genre);
        return remove(genre, GenreRepository.getInstance());
    }

    /**
     * to remove {@link Playlist} which is private and doesn't have any owner-{@link by.platonov.music.entity.User}
     * from {@link PlaylistRepository}
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    public void removeUnusedPlaylists() throws ServiceException {
        try {
            List<Playlist> playlists = PlaylistRepository.getInstance().query(new UnusedPrivatePlaylistsSpecification());
            for (Playlist playlist : playlists) {
                PlaylistRepository.getInstance().remove(playlist);
                log.debug(playlist + " removed");
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * helper method for processing {@link RepositoryException} during removing instance
     * @param t instance of {@link by.platonov.music.entity.Entity}
     * @param repository {@link Repository}
     * @param <T> type of {@link by.platonov.music.entity.Entity}
     * @return true if instance was removed and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private <T> boolean remove(T t, Repository<T> repository) throws ServiceException {
        try {
            return repository.remove(t);
        } catch (RepositoryException e) {
            throw new ServiceException("repository provide exception for Admin service", e);
        }
    }

    /**
     * helper method for processing {@link RepositoryException} during updating instance
     * @param t instance of {@link by.platonov.music.entity.Entity}
     * @param repository {@link Repository}
     * @param <T> type of {@link by.platonov.music.entity.Entity}
     * @return true if instance was updated and false if not
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private <T> boolean update(T t, Repository<T> repository) throws ServiceException {
        try {
            log.debug("Updating entity " + t);
            return repository.update(t);
        } catch (RepositoryException e) {
            throw new ServiceException("could't update " + t, e);
        }
    }

}
