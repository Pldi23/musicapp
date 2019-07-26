package by.platonov.music.repository.specification;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Musician;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.repository.MusicianRepository;
import by.platonov.music.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class EntityNameLimitOffsetSpecificationTest {

    @Test
    void toSqlClauses() throws RepositoryException {
        Repository<Musician> repository = MusicianRepository.getInstance();
        String searchRequest = "л";
        SqlSpecification specification = new EntityNameLimitOffsetSpecification(searchRequest, Integer.MAX_VALUE, 0);
        int actual = repository.query(specification).size();
        int expected = 2;

        assertEquals(expected, actual);
    }
}