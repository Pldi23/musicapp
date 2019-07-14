package by.platonov.music.service;

import by.platonov.music.entity.*;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.*;
import by.platonov.music.repository.specification.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-04.
 * @version 0.0.1
 */
@Log4j2
public class CommonService {

    private final Repository<Track> trackRepository = TrackRepository.getInstance();
    private final Repository<Playlist> playlistRepository = PlaylistRepository.getInstance();
    private final Repository<Musician> musicianRepository = MusicianRepository.getInstance();
    private final Repository<Genre> genreRepository = GenreRepository.getInstance();
    private final Repository<User> userRepository = UserRepository.getInstance();

    public List<Track> searchTrackByName(String trackName) throws ServiceException {
        log.debug("searching tracks in " + trackRepository);
        return search(new SearchNameSpecification(trackName, Integer.MAX_VALUE, 0), trackRepository);
    }

    public List<Track> searchTrackByFilter(String trackname, String genreName, LocalDate fromDate, LocalDate toDate,
                                           String singerName, int limit, long offset) throws ServiceException {
        log.debug("searching tracks in " + trackRepository);
        return tracksWithMusicians(search(new TrackFilterSpecification(trackname, genreName, fromDate, toDate,
                singerName, limit, offset), trackRepository));
    }

    public List<Track> searchTracksByMusician(long musicianId) throws ServiceException {
        log.debug("searching for tracks for musician id: " + musicianId);
        return search(new TracksByMusicianSpecification(musicianId), trackRepository);
    }

    public List<Track> searchTracks(int limit, long offset) throws ServiceException {
        log.debug("searching for tracks in limit: " + limit + " and offset: " + offset);
        return tracksWithMusicians(search(new TrackUuidIsNotNullSpecification(limit, offset), trackRepository));
    }

    public List<Musician> searchMusicians(int limit, long offset) throws ServiceException {
        log.debug("searching for musicians in limit: " + limit + " and offset: " + offset);
        return search(new EntityIdNotNullSpecification(limit, offset), musicianRepository);
    }

    public List<Genre> searchGenres(int limit, long offset) throws ServiceException {
        log.debug("searching for genres in limit: " + limit + " and offset: " + offset);
        return search(new EntityIdNotNullSpecification(limit, offset), genreRepository);
    }

    public List<Playlist> searchPlaylists(int limit, long offset) throws ServiceException {
        log.debug("searching for playlists in limit: " + limit + " and offset: " + offset);
        return search(new EntityIdNotNullSpecification(limit, offset), playlistRepository);
    }

    public List<Track> searchTrackByUuid(String uuid) throws ServiceException {
        log.debug("searching tracks in " + trackRepository);
        return tracksWithMusicians(search(new TrackUuidSpecification(uuid), trackRepository));
    }

    public List<Playlist> searchPlaylist(String playlistName, int limit, long offset) throws ServiceException {
        log.debug("searching playlists in repository");
        return search(new SearchNameSpecification(playlistName, limit, offset), playlistRepository);
    }

    public List<Playlist> searchPlaylistsByTrack(long trackId) throws ServiceException {
        log.debug("searching all playlists which contains track id:" + trackId);
        return search(new PlaylistsWithTrackSpecification(trackId), playlistRepository);
    }

    public List<Musician> searchMusician(String searchRequest, int limit, long offset) throws ServiceException {
        log.debug("searching musicians in repository");
        return search(new SearchNameSpecification(searchRequest, limit, offset), musicianRepository);
    }

    public List<Track> searchTrack(String searchRequest, int limit, long offset) throws ServiceException {
        log.debug("searching musicians in repository");
        return search(new SearchNameSpecification(searchRequest, limit, offset), trackRepository);
    }

    public List<Playlist> searchUserPlaylists(User user) throws ServiceException {
        return search(new PlaylistUserSpecification(user.getLogin()), playlistRepository);
    }

    public Track searchTrackById(String id) throws ServiceException {
        log.debug("searching track by id in trackRepository");
        return tracksWithMusicians(search(new TrackIdSpecification(Long.parseLong(id)), trackRepository)).get(0);
    }

    public Musician searchMusicianById(String id) throws ServiceException {
        return search(new MusicianIdSpecification(Long.parseLong(id)), musicianRepository).get(0);
    }

    public Genre searchGenreById(String id) throws ServiceException {
        return search(new GenreIdSpecification(Long.parseLong(id)), genreRepository).get(0);
    }

    public Playlist searchPlaylistById(String id) throws ServiceException {
        return search(new PlaylistIdSpecification(Long.parseLong(id)), playlistRepository).get(0);
    }

    public Playlist searchPlaylistByIdWitTracks(String id) throws ServiceException {
        Playlist playlist = searchPlaylistById(id);
        playlist.getTracks().addAll(getPlaylistTracks(Long.parseLong(id)));
        return playlist;
    }

    public long countTracks() throws ServiceException {
        return count(new IdIsNotNullSpecification(), trackRepository);
    }

    public long countMusicians() throws ServiceException {
        return count(new IdIsNotNullSpecification(), musicianRepository);
    }

    public long countGenres() throws ServiceException {
        return count(new IdIsNotNullSpecification(), genreRepository);
    }

    public long countPlaylists() throws ServiceException {
        return count(new IdIsNotNullSpecification(), playlistRepository);
    }

    public long countUsersPlaylists(User user) throws ServiceException {
        return count(new PlaylistUserSpecification(user.getLogin()), playlistRepository);
    }

    public List<Track> sortedTracksId(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedIdSpecification(isAscending, limit, offset), trackRepository));
    }

    public List<Track> sortedTracksName(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new EntitySortedNameSpecification(isAscending, limit, offset), trackRepository));
    }

    public List<Track> sortedTracksGenre(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedGenreNameSpecification(isAscending, limit, offset), trackRepository));
    }

    public List<Track> sortedTracksLength(boolean isAscending, int limit, long offset) throws ServiceException {
        return tracksWithMusicians(search(new TrackSortedLengthSpecification(isAscending, limit, offset), trackRepository));
    }

    public List<Musician> sortedMusiciansId(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedIdSpecification(isAscending, limit, offset), musicianRepository);
    }

    public List<Musician> sortedMusiciansName(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedNameSpecification(isAscending, limit, offset), musicianRepository);
    }

    public List<Genre> sortedGenresName(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new GenreSortedTitleSpecification(isAscending, limit, offset), genreRepository);
    }

    public List<Genre> sortedGenresId(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedIdSpecification(isAscending, limit, offset), genreRepository);
    }

    public List<Playlist> sortedPlaylistsId(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedIdSpecification(isAscending, limit, offset), playlistRepository);
    }

    public List<Playlist> sortedPlaylistsName(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new EntitySortedNameSpecification(isAscending, limit, offset), playlistRepository);
    }

    public List<Playlist> sortedPlaylistLength(boolean isAscending, int limit, long offset) throws ServiceException {
        return search(new PlaylistSortedLengthSpecification(isAscending, limit, offset), playlistRepository);
    }

    public Musician getMusician(String musicianName) throws ServiceException {
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
        Genre genre = Genre.builder().title(genreTitle).build();
        try {
            genreRepository.add(genre);
            SqlSpecification specification = new GenreTitleSpecification(genreTitle);
            Genre result = genreRepository.query(specification).get(0);
            log.debug("getting " + result + " from repository");
            return result;
        } catch (RepositoryException e) {
            throw new ServiceException("genre repository provide an exception to service" + e);
        }
    }

    public List<Track> getRandomTen() throws ServiceException {
        log.debug("getting 10 random tracks");
        return search(new TrackRandomSpecification(), trackRepository);
    }

    public boolean createPlaylist(User user, String playlistName) throws ServiceException {
        try {
            Playlist playlist = Playlist.builder().name(playlistName).tracks(new HashSet<>()).build();
            boolean additionResult = playlistRepository.add(playlist);
            user.getPlaylists().add(playlist);
            boolean updatingResult = userRepository.update(user);
            return additionResult && updatingResult;
        } catch (RepositoryException e) {
            log.error("exception from repository ", e);
            throw new ServiceException(e);
        }
    }

    private List<Track> tracksWithMusicians(List<Track> tracks) throws ServiceException {
        for (Track track : tracks) {
            track.getSingers().addAll(getTrackSingers(track.getId()));
            track.getAuthors().addAll(getTrackAuthors(track.getId()));
        }
        return tracks;
    }

    private List<Track> getPlaylistTracks(long playlistId) throws ServiceException {
        return tracksWithMusicians(search(new TracksInPlaylistSpecification(playlistId), trackRepository));
    }

    private List<Musician> getTrackSingers(long trackId) throws ServiceException {
        return search(new TrackSingersSpecification(trackId), musicianRepository);
    }

    private List<Musician> getTrackAuthors(long trackId) throws ServiceException {
        return search(new TrackAuthorsSpecification(trackId), musicianRepository);
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
            throw new ServiceException("exception from repository", e);
        }
    }


}
