package by.platonov.music.service;

import by.platonov.music.entity.Musician;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.MusicianRepository;
import by.platonov.music.repository.Repository;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
public class MusicianService {

    private Repository<Musician> repository = MusicianRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Musician> search(String searchRequest) throws ServiceException {
        return serviceHelper.search(searchRequest, repository);
    }
}
