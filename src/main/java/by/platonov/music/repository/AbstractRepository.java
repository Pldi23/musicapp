package by.platonov.music.repository;

import by.platonov.music.entity.*;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SqlSpecification;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-12.
 * @version 0.0.1
 */
@Log4j2
public abstract class AbstractRepository<Entity> implements Repository<Entity> {

    public abstract List<Entity> buildEntityList(ResultSet resultSet) throws SQLException;
    public abstract Entity buildEntity(ResultSet resultSet) throws SQLException;
    public abstract String buildFindOneSqlRequest();
    public abstract String buildCountSqlRequest();
    public abstract String buildQuerySqlRequest();
    public abstract List<PreparedStatement> insertStatements(Connection connection, Entity entity) throws SQLException;
    public abstract List<PreparedStatement> removeStatements(Connection connection, Entity entity) throws SQLException;
    public abstract List<PreparedStatement> updateStatements(Connection connection, Entity entity) throws SQLException;
    public abstract boolean isPresent(Connection connection, Entity entity) throws RepositoryException;

    @Override
    public boolean add(Entity entity) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            if (!isPresent(connection, entity)) {
                try {
                    for (PreparedStatement statement : insertStatements(connection, entity)) {
                        statement.execute();
                    }
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                return false;
            }
        });
    }

    @Override
    public boolean remove(Entity entity) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            if (isPresent(connection, entity)) {
                try {
                    for (PreparedStatement statement : removeStatements(connection, entity)) {
                        statement.execute();
                    }
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                return false;
            }
        });
    }

    @Override
    public boolean update(Entity entity) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            if (isPresent(connection, entity)) {
                try {
                    for (PreparedStatement statement : updateStatements(connection, entity)) {
                        statement.execute();
                    }
                    return true;
                } catch (SQLException e) {
                    throw new RepositoryException(e);
                }
            } else {
                return false;
            }
        });
    }

    @Override
    public List<Entity> query(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(buildQuerySqlRequest() + specification.toSqlClauses());
                ResultSet resultSet = statement.executeQuery()) {
                    return buildEntityList(resultSet);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }

    @Override
    public Optional<Entity> findOne(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> findOneNonTransactional(connection, specification));
    }

    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(buildCountSqlRequest() + specification.toSqlClauses());
                ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }

    protected Optional<Entity> findOneNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(buildFindOneSqlRequest() + specification.toSqlClauses());
             ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() ? Optional.of(buildEntity(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    protected Playlist buildPlaylist(ResultSet resultSet) throws SQLException {
        Set<Track> tracks = new TreeSet<>();
        Playlist playlist = Playlist.builder()
                .id(resultSet.getLong("playlistid"))
                .name(resultSet.getString("playlistname"))
                .tracks(tracks)
                .build();
        Track track = buildTrack(resultSet);
        tracks.add(track);
        return playlist;
    }

    protected Track buildTrack(ResultSet resultSet) throws SQLException {
        Set<Musician> singers = new HashSet<>();
        Set<Musician> authors = new HashSet<>();
        Track track = Track.builder()
                .id(resultSet.getLong("trackid"))
                .name(resultSet.getString("trackname"))
                .genre(buildGenre(resultSet))
                .length(resultSet.getLong("length"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .singers(singers)
                .authors(authors)
                .build();
        Musician singer = buildSinger(resultSet);
        singers.add(singer);
        Musician author = buildAuthor(resultSet);
        authors.add(author);
        return track;
    }

    protected Musician buildSinger(ResultSet resultSet) throws SQLException {
        return Musician.builder()
                .id(resultSet.getLong("singerid"))
                .name(resultSet.getString("singername"))
                .build();
    }

    protected Musician buildAuthor(ResultSet resultSet) throws SQLException {
        return Musician.builder()
                .id(resultSet.getLong("authorid"))
                .name(resultSet.getString("authorname"))
                .build();
    }

    protected Genre buildGenre(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("genreid"))
                .title(resultSet.getString("genre"))
                .build();
    }

    //todo
    protected User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder().build();
    }
}
