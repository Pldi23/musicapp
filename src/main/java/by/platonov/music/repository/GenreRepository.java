package by.platonov.music.repository;

import by.platonov.music.entity.Genre;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.specification.SqlSpecification;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author dzmitryplatonov on 2019-06-10.
 * @version 0.0.1
 */
public class GenreRepository implements Repository<Genre> {

    @Language("SQL")
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM genre WHERE ";
    @Language("SQL")
    private static final String SQL_SELECT_ONE = "SELECT id, name FROM genre WHERE ";

    @Override
    public boolean add(Genre entity) throws RepositoryException {
        return false;
    }

    @Override
    public boolean remove(Genre entity) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(Genre entity) throws RepositoryException {
        return false;
    }

    @Override
    public List<Genre> query(SqlSpecification specification) throws RepositoryException {
        return null;
    }

    @Override
    public int count(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(SQL_COUNT + specification.toSqlClauses());
                ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new RepositoryException(e);
            }
        });
    }

    @Override
    public Optional<Genre> findOne(SqlSpecification specification) throws RepositoryException {
        return TransactionHandler.transactional(connection -> findOneNonTransactional(connection, specification));
    }

    private Optional<Genre> findOneNonTransactional(Connection connection, SqlSpecification specification) throws RepositoryException {
        try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ONE + specification.toSqlClauses());
            ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(Genre.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("name"))
                    .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
