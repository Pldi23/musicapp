package by.platonov.music.service;

import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.MusicianRepository;
import by.platonov.music.repository.PlaylistRepository;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.specification.SearchSpecification;
import by.platonov.music.repository.specification.SqlSpecification;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
public class PlaylistService {

    private Repository<Playlist> repository = PlaylistRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Playlist> search(String searchRequest) throws ServiceException {
        return serviceHelper.search(searchRequest, repository);
    }
}
