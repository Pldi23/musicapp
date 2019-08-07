package by.platonov.music.service;

import by.platonov.music.entity.*;
import by.platonov.music.entity.filter.EntityFilter;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.*;
import by.platonov.music.repository.specification.*;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.List;

/**
 * encapsulates common-level business logic to provide needed data to command layer
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class CommonService {

    /**
     * method to search in {@link TrackRepository} for {@link Track} with required name
     * @param trackName string name of the track
     * @return list of one track if track with param name is found, and empty list if track with that name not exists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> searchTrackByName(String trackName) throws ServiceException {
        log.debug("searching tracks in track repository");
        return search(new EntityNameLimitOffsetSpecification(trackName, Integer.MAX_VALUE, 0), TrackRepository.getInstance());
    }

    /**
     * to search in {@link TrackRepository} for list of {@link Track}s with
     * required {@link by.platonov.music.entity.filter.TrackFilter} parameters
     * @param entityFilter {@link by.platonov.music.entity.filter.TrackFilter}
     * @param limit number of max needed tracks
     * @param offset offset
     * @return list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> searchTrackByFilter(EntityFilter entityFilter, int limit, long offset) throws ServiceException {
        log.debug("searching tracks in track repository");
        return tracksWithMusicians(search(new TrackFilterSpecification(entityFilter, limit, offset), TrackRepository.getInstance()));
    }

    /**
     * to search in {@link TrackRepository} for list of {@link Track}s that performed by {@link Musician}
     * @param musicianId unique id of the musician
     * @return list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> searchTracksByMusician(long musicianId) throws ServiceException {
        log.debug("searching for tracks for musician id: " + musicianId);
        return search(new TracksByMusicianSpecification(musicianId), TrackRepository.getInstance());
    }

    /**
     * to search in {@link TrackRepository} for list of all {@link Track}s
     * @param limit number of max needed tracks
     * @param offset offset
     * @return list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> searchTracks(int limit, long offset) throws ServiceException {
        log.debug("searching for tracks in limit: " + limit + " and offset: " + offset);
        return tracksWithMusicians(search(new TrackUuidIsNotNullSpecification(limit, offset), TrackRepository.getInstance()));
    }

    /**
     * to search in {@link MusicianRepository} for list of {@link Musician}s
     * @param limit number of max needed musicians
     * @param offset offset
     * @return list of musicians
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Musician> searchMusicians(int limit, long offset) throws ServiceException {
        log.debug("searching for musicians in limit: " + limit + " and offset: " + offset);
        return search(new EntityIdNotNullLimitOffsetSpecification(limit, offset), MusicianRepository.getInstance());
    }

    /**
     * to search in {@link PlaylistRepository} for list of {@link Playlist}s depending on user role
     * @param limit number of max needed playlists
     * @param offset offset
     * @param isAdmin user role
     * @return list of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> searchPlaylists(int limit, long offset, boolean isAdmin) throws ServiceException {
        log.debug("searching for playlists in limit: " + limit + " and offset: " + offset);
        return playlistsWithTracks(search(new PlaylistPublicLimitOffsetSpecification(limit, offset, isAdmin), PlaylistRepository.getInstance()));
    }

    /**
     * to search in {@link PlaylistRepository} for list of {@link Playlist}s by name, depending on user role
     * @return list of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> searchPlaylistByName(String playlistName, int limit, long offset, User user) throws ServiceException {
        log.debug("searching playlists in repository");
        return playlistsWithTracks(search(new PlaylistByNameAndUserTypeLimitOffsetSpecification(playlistName, limit,
                offset, user), PlaylistRepository.getInstance()));
    }

    /**
     * to search in {@link MusicianRepository} for list of {@link Musician}s by search request
     * @param searchRequest string-request param
     * @param limit number of max needed musicians
     * @param offset offset
     * @return list of musicians
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Musician> searchMusician(String searchRequest, int limit, long offset) throws ServiceException {
        log.debug("searching musicians in repository");
        return search(new EntityNameLimitOffsetSpecification(searchRequest, limit, offset), MusicianRepository.getInstance());
    }

    /**
     * to search in {@link TrackRepository} for list of {@link Track}s by search request
     * @param searchRequest string request param
     * @param limit number of max needed tracks
     * @param offset offset
     * @return list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> searchTrack(String searchRequest, int limit, long offset) throws ServiceException {
        log.debug("searching musicians in repository");
        return search(new EntityNameLimitOffsetSpecification(searchRequest, limit, offset), TrackRepository.getInstance());
    }

    /**
     * to search in {@link PlaylistRepository} for playlist that user has
     * @param user user
     * @return list of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> searchUserPlaylists(User user) throws ServiceException {
        return playlistsWithTracks(search(new PlaylistOwnedByUserSpecification(user.getLogin()), PlaylistRepository.getInstance()));
    }

    /**
     * method to search in {@link TrackRepository} for {@link Track} with required id
     * @param id track id
     * @return list of one track if track with param id is found, and empty list if track with that id not exists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> searchTrackById(String id) throws ServiceException {
        log.debug("searching track by id in trackRepository");
        return tracksWithMusicians(search(new TrackIdSpecification(Long.parseLong(id)), TrackRepository.getInstance()));
    }

    /**
     * method to search in {@link MusicianRepository} for {@link Musician} with required id
     * @param id musician id
     * @return list of one musician if musician with param id is found, and empty list if musician with that id not exists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Musician> searchMusicianById(String id) throws ServiceException {
        return search(new MusicianIdSpecification(Long.parseLong(id)), MusicianRepository.getInstance());
    }

    /**
     * method to search in {@link GenreRepository} for {@link Genre} with required id
     * @param id genre id
     * @return list of one genre if genre with param id is found, and empty list if genre with that id not exists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Genre> searchGenreById(String id) throws ServiceException {
        return search(new GenreIdSpecification(Long.parseLong(id)), GenreRepository.getInstance());
    }

    /**
     * method to search in {@link PlaylistRepository} for {@link Playlist} by required id
     * @param id playlist id
     * @return list of one playlist if playlist with param id is found, and empty list if playlists with that id not exists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> searchPlaylistById(String id) throws ServiceException {
        return search(new PlaylistIdSpecification(Long.parseLong(id)), PlaylistRepository.getInstance());
    }

    /**
     * method to search in {@link PlaylistRepository} for {@link Playlist} with songs by required id.
     * @param id playlist id
     * @return list of one playlist if playlist with param id is found, and empty list if playlists with that id not exists
     * received playlists contain songs
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> searchPlaylistByIdWithTracks(String id) throws ServiceException {
        List<Playlist> playlists = searchPlaylistById(id);
        for (Playlist playlist : playlists) {
            playlist.getTracks().addAll(getPlaylistTracks(Long.parseLong(id)));
        }
        return playlists;
    }

    /**
     * method to search in {@link PlaylistRepository} for {@link Playlist} by user and track id
     * @param trackId track id
     * @param user user
     * @return list of playlist
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> searchPlaylistsByTrackAndUser(long trackId, User user) throws ServiceException {
        return search(new PlaylistByTrackAndUserSpecification(trackId, user.getLogin()), PlaylistRepository.getInstance());
    }

    /**
     * to count total quantity of tracks
     * @return total quantity of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public long countTracks() throws ServiceException {
        return count(new TrackIdIsNotNullSpecification(), TrackRepository.getInstance());
    }

    /**
     * to count total quantity of musicians in repository
     * @return total quantity of musicians
     * @throws ServiceException if repository throws Repository exception
     */
    public long countMusicians() throws ServiceException {
        return count(new IdIsNotNullSpecification(), MusicianRepository.getInstance());
    }

    /**
     * to count total quantity of playlists
     * @return total quantity of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public long countPlaylists(boolean isAdmin) throws ServiceException {
        return count(new PlaylistPublicLimitOffsetSpecification(Integer.MAX_VALUE, 0, isAdmin), PlaylistRepository.getInstance());
    }

    /**
     * to receive list of tracks sorted by id
     * @param isAscending sort order
     * @param limit number of needed tracks
     * @param offset offset
     * @return sorted list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> sortedTracksId(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedIdSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    /**
     * to receive list of tracks sorted by name
     * @param isAscending sort order
     * @param limit number of needed tracks
     * @param offset offset
     * @return sorted list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> sortedTracksName(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new EntitySortedNameSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    /**
     * to receive list of tracks sorted by genre title
     * @param isAscending sort order
     * @param limit number of needed tracks
     * @param offset offset
     * @return sorted list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> sortedTracksGenre(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedGenreNameSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    /**
     * to receive list of tracks sorted by track length(duration)
     * @param isAscending sort order
     * @param limit number of needed tracks
     * @param offset offset
     * @return sorted list of tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> sortedTracksLength(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedLengthSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    /**
     * to receive list of musicians sorted by id
     * @param isAscending sort order
     * @param limit number of needed musicians
     * @param offset offset
     * @return sorted list of musicians
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Musician> sortedMusiciansId(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedIdSpecification(isAscending, limit, offset), MusicianRepository.getInstance());
    }

    /**
     * to receive list of musicians sorted by name
     * @param isAscending sort order
     * @param limit number of needed musicians
     * @param offset offset
     * @return sorted list of musicians
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Musician> sortedMusiciansName(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedNameSpecification(isAscending, limit, offset), MusicianRepository.getInstance());
    }

    /**
     * to receive list of playlists sorted by id
     * @param isAscending sort order
     * @param limit number of needed playlists
     * @param offset offset
     * @return sorted list of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> sortedPlaylistsId(boolean isAscending, int limit, long offset) throws ServiceException {
        return playlistsWithTracks(search(new EntitySortedIdSpecification(isAscending, limit, offset),
                PlaylistRepository.getInstance()));
    }

    /**
     * to receive list of playlists sorted by name
     * @param isAscending sort order
     * @param limit number of needed playlists
     * @param offset offset
     * @return sorted list of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> sortedPlaylistsName(boolean isAscending, int limit, long offset, boolean isAdmin) throws ServiceException {
        return playlistsWithTracks(search(new PlaylistSortedNameSpecification(isAscending, limit, offset, isAdmin),
                PlaylistRepository.getInstance()));
    }

    /**
     * to receive list of playlists sorted by length (total tracks duration)
     * @param isAscending sort order
     * @param limit number of needed playlists
     * @param offset offset
     * @return sorted list of playlists
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Playlist> sortedPlaylistLength(boolean isAscending, int limit, long offset, boolean isAdmin) throws ServiceException {
        return playlistsWithTracks(search(new PlaylistSortedLengthSpecification(isAscending, limit, offset, isAdmin),
                PlaylistRepository.getInstance()));
    }

    /**
     * to get musician from {@link MusicianRepository} by name, or if it is not exist, add entity to repository and get it thereafter
     * @param musicianName name
     * @return musician
     * @throws ServiceException if repository throws Repository exception
     */
    public Musician getOrAddMusician(String musicianName) throws ServiceException {
        MusicianRepository musicianRepository = MusicianRepository.getInstance();
        Musician resultMusician;
        Musician musician = Musician.builder().name(musicianName).build();
        SqlSpecification specification = new MusicianNameSpecification(musicianName);
        List<Musician> musicians;
        try {
            musicians = musicianRepository.query(specification);
            if (musicians.isEmpty()) {
                log.debug("adding " + musicianName + " to repository");
                musicianRepository.add(musician);
                log.debug("getting " + musicianName + " from repository");
                resultMusician = musicianRepository.query(specification).get(0);
            } else {
                log.debug("getting " + musicianName + " from repository");
                resultMusician = musicians.get(0);
            }
            return resultMusician;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * to get {@link Genre} from {@link GenreRepository} by title
     * @param genreTitle title
     * @return genre
     * @throws ServiceException if repository throws Repository exception
     */
    public Genre getGenre(String genreTitle) throws ServiceException {
        GenreRepository genreRepository = GenreRepository.getInstance();
        Genre genre = Genre.builder().title(genreTitle).build();
        try {
            genreRepository.add(genre);
            SqlSpecification specification = new GenreTitleSpecification(genreTitle);
            Genre result = genreRepository.query(specification).get(0);
            log.debug(result + " genre was recieved from repository");
            return result;
        } catch (RepositoryException e) {
            throw new ServiceException("genre repository provide an exception to service", e);
        }
    }

    /**
     * to get ten random tracks
     * @return list of ten tracks chosen randomly
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> getRandomTen() throws ServiceException {
        log.debug("getting 10 random tracks");
        return tracksWithMusicians(search(new TrackRandomSpecification(), TrackRepository.getInstance()));
    }

    /**
     * to get last ten tracks that was added to {@link TrackRepository}
     * @return list of ten tracks
     * @throws ServiceException if repository throws Repository exception
     */
    public List<Track> getTracksLastAdded() throws ServiceException {
        log.debug("getting 10 tracks last added");
        return tracksWithMusicians(search(new TracksLastAddedSpecification(), TrackRepository.getInstance()));
    }

    /**
     * to create a {@link Playlist}
     * @param user user that wants to create a playlist
     * @param isPersonal if user want it to be private or not( if user is not admin isPersonal automatically sets to true)
     * @param playlistName name of playlist
     * @return true if playlist was created and false if not
     * @throws ServiceException if repository throws Repository exception
     */
    public boolean createPlaylist(User user, boolean isPersonal, String playlistName) throws ServiceException {
        try {
            Playlist playlist = Playlist.builder().name(playlistName).tracks(new LinkedList<>()).personal(isPersonal).build();
            boolean additionResult = PlaylistRepository.getInstance().add(playlist);
            user.getPlaylists().add(playlist);
            boolean updatingResult = UserRepository.getInstance().update(user);
            return additionResult && updatingResult;
        } catch (RepositoryException e) {
            log.error("playlist could not be created in repository", e);
            throw new ServiceException(e);
        }
    }

    /**
     * to add {@link Track} to {@link Playlist}
     * @param trackId id of the track that is wanted to add
     * @param playlistId id of the playlist you want to add the track to
     * @return true if operation completed and false if not
     * @throws ServiceException if repository throws Repository exception
     */
    public boolean addTrackToPLaylist(String trackId, String playlistId) throws ServiceException {
        try {
            Track track = TrackRepository.getInstance().query(new TrackIdSpecification(Long.parseLong(trackId))).get(0);
            Playlist playlist = PlaylistRepository.getInstance()
                    .query(new PlaylistIdSpecification(Long.parseLong(playlistId))).get(0);
            List<Track> tracks = getPlaylistTracks(playlist.getId());
            tracks.add(track);
            playlist.getTracks().addAll(tracks);
            return PlaylistRepository.getInstance().update(playlist);
        } catch (RepositoryException e) {
            log.error("track could not be added to playlist in repository", e);
            throw new ServiceException(e);
        }
    }

    /**
     * to remove track from playlist
     * @param trackId id of the track that is wanted to remove
     * @param playlistId id of the playlist you want to remove the track from
     * @return true if operation completed and false if not
     * @throws ServiceException if repository throws Repository exception
     */
    public boolean removeTrackFromPlaylist(String trackId, String playlistId) throws ServiceException {
        boolean result = true;
        try {
            List<Playlist> playlists = searchPlaylistByIdWithTracks(playlistId);
            for (Playlist playlist : playlists) {
                playlist.getTracks().removeIf(t -> t.getId() == Long.parseLong(trackId));
                result = PlaylistRepository.getInstance().update(playlist);
            }
        } catch (RepositoryException e) {
            log.error("could not remove track from playlist", e);
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * to remove {@link Playlist} from {@link User}
     * @param user id of the user which is playlist owner
     * @param playlistId id of the playlist that you want to remove
     * @return true if operation completed and false if not
     * @throws ServiceException if repository throws Repository exception
     */
    public boolean removePlaylistFromUser(User user, String playlistId) throws ServiceException {
        user.getPlaylists().removeIf(playlist -> playlist.getId() == Long.parseLong(playlistId));
        try {
            return UserRepository.getInstance().update(user);
        } catch (RepositoryException e) {
            log.error("repository could not update user ", e);
            throw new ServiceException(e);
        }
    }

    /**
     * to count playlist time-duration
     * @param playlist playlist
     * @return formatted string of calculated time value
     * @throws ServiceException if repository throws Repository exception
     */
    public String countPlaylistLength(Playlist playlist) throws ServiceException {
        playlist.getTracks().addAll(getPlaylistTracks(playlist.getId()));
        return playlist.getTotalDuration();
    }

    /**
     * to count playlist quantity of tracks
     * @param playlist playlist being reviewed
     * @return string representation of counted value
     * @throws ServiceException if repository throws Repository exception
     */
    public String countPlaylistSize(Playlist playlist) throws ServiceException {
        return String.valueOf(getPlaylistTracks(playlist.getId()).size());
    }

    /**
     * helper method to get tracks with theirs singers and authors
     * @param tracks list of tracks which is needed to be upgraded
     * @return list of tracks with theirs singers and authors
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private List<Track> tracksWithMusicians(List<Track> tracks) throws ServiceException {
        for (Track track : tracks) {
            track.getSingers().addAll(getTrackSingers(track.getId()));
            track.getAuthors().addAll(getTrackAuthors(track.getId()));
        }
        return tracks;
    }

    /**
     * helper method to get list of tracks from required playlist
     * @param playlistId id of {@link Playlist}
     * @return list of tracks
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private List<Track> getPlaylistTracks(long playlistId) throws ServiceException {
        return tracksWithMusicians(search(new TracksInPlaylistSpecification(playlistId), TrackRepository.getInstance()));
    }

    /**
     * helper method for filling {@link Playlist} with {@link Track}s
     * @param playlists list of playlists for filling
     * @return list of playlists
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private List<Playlist> playlistsWithTracks(List<Playlist> playlists) throws ServiceException {
        for (Playlist playlist : playlists) {
            playlist.getTracks().addAll(getPlaylistTracks(playlist.getId()));
        }
        return playlists;
    }

    /**
     * helper method to get list of singers by {@link Track} id
     * @param trackId track id
     * @return list of {@link Musician}s
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private List<Musician> getTrackSingers(long trackId) throws ServiceException {
        return search(new TrackSingersSpecification(trackId), MusicianRepository.getInstance());
    }

    /**
     * helper method to get list of authors by {@link Track} id
     * @param trackId track id
     * @return list of {@link Musician}s
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private List<Musician> getTrackAuthors(long trackId) throws ServiceException {
        return search(new TrackAuthorsSpecification(trackId), MusicianRepository.getInstance());
    }

    /**
     * helper method to search list of {@link Entity}s using {@link SqlSpecification} and {@link Repository}
     * used to process {@link RepositoryException}
     * @param specification {@link SqlSpecification} specifying search request
     * @param repository implementation of {@link Repository} for example {@link TrackRepository}
     * @param <T> type of {@link Entity}
     * @return list of entities
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private <T> List<T> search(SqlSpecification specification, Repository<T> repository) throws ServiceException {
        try {
            return repository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("repository provide exception for Common service", e);
        }
    }

    /**
     * helper method for processing {@link RepositoryException} during counting instances
     * @param specification {@link SqlSpecification} specifying search request
     * @param repository implementation of {@link Repository} for example {@link TrackRepository}
     * @return list of entities
     * @throws ServiceException if repository throws {@link RepositoryException}
     */
    private long count(SqlSpecification specification, Repository repository) throws ServiceException {
        try {
            return repository.count(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("exception while counting repository entities", e);
        }
    }


}
