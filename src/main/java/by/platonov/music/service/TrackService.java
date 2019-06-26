package by.platonov.music.service;

import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.repository.Repository;
import by.platonov.music.repository.TrackRepository;

import java.util.List;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
public class TrackService {

    private Repository<Track> repository = TrackRepository.getInstance();
    private ServiceHelper serviceHelper = new ServiceHelper();

    public List<Track> search(String searchRequest) throws ServiceException {
        return serviceHelper.search(searchRequest, repository);
    }
}
