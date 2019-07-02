package by.platonov.music.service;

import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.PlaylistRepository;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.specification.SearchNameSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistService {

    private Repository<Playlist> repository = PlaylistRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Playlist> search(String searchRequest) throws ServiceException {
        log.debug("searching playlists in " + repository);
        return serviceHelper.search(new SearchNameSpecification(searchRequest), repository);
    }
}
