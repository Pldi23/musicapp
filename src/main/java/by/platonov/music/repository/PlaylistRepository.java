package by.platonov.music.repository;

import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SqlSpecification;

import java.util.List;
import java.util.Optional;

/**
 * @author dzmitryplatonov on 2019-06-11.
 * @version 0.0.1
 */
public class PlaylistRepository implements Repository<Playlist> {
    @Override
    public boolean add(Playlist entity) throws RepositoryException {
        return false;
    }

    @Override
    public boolean remove(Playlist entity) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(Playlist entity) throws RepositoryException {
        return false;
    }

    @Override
    public List<Playlist> query(SqlSpecification specification) throws RepositoryException {
        return null;
    }

    @Override
    public Optional<Playlist> findOne(SqlSpecification specification) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return 0;
    }
}
