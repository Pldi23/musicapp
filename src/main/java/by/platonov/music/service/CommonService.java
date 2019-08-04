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
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class CommonService {

    public List<Track> searchTrackByName(String trackName) throws ServiceException {
        log.debug("searching tracks in track repository");
        return search(new EntityNameLimitOffsetSpecification(trackName, Integer.MAX_VALUE, 0), TrackRepository.getInstance());
    }

    public List<Track> searchTrackByFilter(EntityFilter entityFilter, int limit, long offset) throws ServiceException {
        log.debug("searching tracks in track repository");
        return tracksWithMusicians(search(new TrackFilterSpecification(entityFilter, limit, offset), TrackRepository.getInstance()));
    }

    public List<Track> searchTracksByMusician(long musicianId) throws ServiceException {
        log.debug("searching for tracks for musician id: " + musicianId);
        return search(new TracksByMusicianSpecification(musicianId), TrackRepository.getInstance());
    }

    public List<Track> searchTracks(int limit, long offset) throws ServiceException {
        log.debug("searching for tracks in limit: " + limit + " and offset: " + offset);
        return tracksWithMusicians(search(new TrackUuidIsNotNullSpecification(limit, offset), TrackRepository.getInstance()));
    }

    public List<Musician> searchMusicians(int limit, long offset) throws ServiceException {
        log.debug("searching for musicians in limit: " + limit + " and offset: " + offset);
        return search(new EntityIdNotNullLimitOffsetSpecification(limit, offset), MusicianRepository.getInstance());
    }

    public List<Playlist> searchPlaylists(int limit, long offset, boolean isAdmin) throws ServiceException {
        log.debug("searching for playlists in limit: " + limit + " and offset: " + offset);
        return playlistsWithTracks(search(new PlaylistPublicLimitOffsetSpecification(limit, offset, isAdmin), PlaylistRepository.getInstance()));
    }

    public List<Playlist> searchPlaylistByName(String playlistName, int limit, long offset, User user) throws ServiceException {
        log.debug("searching playlists in repository");
        return playlistsWithTracks(search(new PlaylistByNameAndUserTypeLimitOffsetSpecification(playlistName, limit,
                offset, user), PlaylistRepository.getInstance()));
    }

    public List<Playlist> searchPlaylistsByTrack(long trackId) throws ServiceException {
        log.debug("searching all playlists which contains track id:" + trackId);
        return search(new PlaylistsWithTrackSpecification(trackId), PlaylistRepository.getInstance());
    }

    public List<Musician> searchMusician(String searchRequest, int limit, long offset) throws ServiceException {
        log.debug("searching musicians in repository");
        return search(new EntityNameLimitOffsetSpecification(searchRequest, limit, offset), MusicianRepository.getInstance());
    }

    public List<Track> searchTrack(String searchRequest, int limit, long offset) throws ServiceException {
        log.debug("searching musicians in repository");
        return search(new EntityNameLimitOffsetSpecification(searchRequest, limit, offset), TrackRepository.getInstance());
    }

    public List<Playlist> searchUserPlaylists(User user) throws ServiceException {
        return playlistsWithTracks(search(new PlaylistOwnedByUserSpecification(user.getLogin()), PlaylistRepository.getInstance()));
    }

    public List<Track> searchTrackById(String id) throws ServiceException {
        log.debug("searching track by id in trackRepository");
        return tracksWithMusicians(search(new TrackIdSpecification(Long.parseLong(id)), TrackRepository.getInstance()));
    }

    public List<Musician> searchMusicianById(String id) throws ServiceException {
        return search(new MusicianIdSpecification(Long.parseLong(id)), MusicianRepository.getInstance());
    }

    public List<Genre> searchGenreById(String id) throws ServiceException {
        return search(new GenreIdSpecification(Long.parseLong(id)), GenreRepository.getInstance());
    }

    public List<Playlist> searchPlaylistById(String id) throws ServiceException {
        return search(new PlaylistIdSpecification(Long.parseLong(id)), PlaylistRepository.getInstance());
    }

    public List<Playlist> searchPlaylistByIdWithTracks(String id) throws ServiceException {
        List<Playlist> playlists = searchPlaylistById(id);
        for (Playlist playlist : playlists) {
            playlist.getTracks().addAll(getPlaylistTracks(Long.parseLong(id)));
        }
        return playlists;
    }

    public List<Playlist> searchPlaylistsByTrackAndUser(long trackId, User user) throws ServiceException {
        return search(new PlaylistByTrackAndUserSpecification(trackId, user.getLogin()), PlaylistRepository.getInstance());
    }

    public long countTracks() throws ServiceException {
        return count(new TrackIdIsNotNullSpecification(), TrackRepository.getInstance());
    }

    public long countMusicians() throws ServiceException {
        return count(new IdIsNotNullSpecification(), MusicianRepository.getInstance());
    }

    public long countPlaylists(boolean isAdmin) throws ServiceException {
        return count(new PlaylistPublicLimitOffsetSpecification(Integer.MAX_VALUE, 0, isAdmin), PlaylistRepository.getInstance());
    }

    public List<Track> sortedTracksId(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedIdSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    public List<Track> sortedTracksName(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new EntitySortedNameSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    public List<Track> sortedTracksGenre(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedGenreNameSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    public List<Track> sortedTracksLength(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedLengthSpecification(isAscending, limit, offset), TrackRepository.getInstance()));
    }

    public List<Musician> sortedMusiciansId(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedIdSpecification(isAscending, limit, offset), MusicianRepository.getInstance());
    }

    public List<Musician> sortedMusiciansName(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedNameSpecification(isAscending, limit, offset), MusicianRepository.getInstance());
    }

    public List<Playlist> sortedPlaylistsId(boolean isAscending, int limit, long offset) throws ServiceException {
        return playlistsWithTracks(search(new EntitySortedIdSpecification(isAscending, limit, offset),
                PlaylistRepository.getInstance()));
    }

    public List<Playlist> sortedPlaylistsName(boolean isAscending, int limit, long offset, boolean isAdmin) throws ServiceException {
        return playlistsWithTracks(search(new PlaylistSortedNameSpecification(isAscending, limit, offset, isAdmin),
                PlaylistRepository.getInstance()));
    }

    public List<Playlist> sortedPlaylistLength(boolean isAscending, int limit, long offset, boolean isAdmin) throws ServiceException {
        return playlistsWithTracks(search(new PlaylistSortedLengthSpecification(isAscending, limit, offset, isAdmin),
                PlaylistRepository.getInstance()));
    }

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

    public List<Track> getRandomTen() throws ServiceException {
        log.debug("getting 10 random tracks");
        return tracksWithMusicians(search(new TrackRandomSpecification(), TrackRepository.getInstance()));
    }

    public List<Track> getTracksLastAdded() throws ServiceException {
        log.debug("getting 10 tracks last added");
        return tracksWithMusicians(search(new TracksLastAddedSpecification(), TrackRepository.getInstance()));
    }

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

    public boolean removePlaylistFromUser(User user, String playlistId) throws ServiceException {
        user.getPlaylists().removeIf(playlist -> playlist.getId() == Long.parseLong(playlistId));
        try {
            return UserRepository.getInstance().update(user);
        } catch (RepositoryException e) {
            log.error("repository could not update user ", e);
            throw new ServiceException(e);
        }
    }

    public String countPlaylistLength(Playlist playlist) throws ServiceException {
        playlist.getTracks().addAll(getPlaylistTracks(playlist.getId()));
        return playlist.getTotalDuration();
    }

    public String countPlaylistSize(Playlist playlist) throws ServiceException {
        return String.valueOf(getPlaylistTracks(playlist.getId()).size());
    }

    private List<Track> tracksWithMusicians(List<Track> tracks) throws ServiceException {
        for (Track track : tracks) {
            track.getSingers().addAll(getTrackSingers(track.getId()));
            track.getAuthors().addAll(getTrackAuthors(track.getId()));
        }
        return tracks;
    }

    private List<Track> getPlaylistTracks(long playlistId) throws ServiceException {
        return tracksWithMusicians(search(new TracksInPlaylistSpecification(playlistId), TrackRepository.getInstance()));
    }

    private List<Playlist> playlistsWithTracks(List<Playlist> playlists) throws ServiceException {
        for (Playlist playlist : playlists) {
            playlist.getTracks().addAll(getPlaylistTracks(playlist.getId()));
        }
        return playlists;
    }

    private List<Musician> getTrackSingers(long trackId) throws ServiceException {
        return search(new TrackSingersSpecification(trackId), MusicianRepository.getInstance());
    }

    private List<Musician> getTrackAuthors(long trackId) throws ServiceException {
        return search(new TrackAuthorsSpecification(trackId), MusicianRepository.getInstance());
    }

    private <T> List<T> search(SqlSpecification specification, Repository<T> repository) throws ServiceException {
        try {
            return repository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("repository provide exception for Common service", e);
        }
    }

    private long count(SqlSpecification specification, Repository repository) throws ServiceException {
        try {
            return repository.count(specification);
        } catch (RepositoryException e) {
            throw new ServiceException("exception while counting repository entities", e);
        }
    }


}
