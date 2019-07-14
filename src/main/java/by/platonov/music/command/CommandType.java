package by.platonov.music.command;

import by.platonov.music.service.*;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public enum CommandType {

    ACTIVATION(new ActivationCommand(new UserService())),
    BACK(new BackPageCommand()),
    ERROR(new ErrorCommand()),
    FILTER_TRACK(new FilterTrackCommand(new CommonService())),
    LOGIN(new LoginCommand(new UserService())),
    LOGOUT(new LogoutCommand()),
    MUSICIAN_DETAIL(new MusicianDetailCommand(new CommonService())),
    PLAYLIST_DETAIL(new PlaylistDetailCommand(new CommonService())),
    PLAYLIST_CREATE(new PlaylistCreateCommand(new CommonService())),
    QUERY(new QueryCommand(new CommonService())),
    REGISTER(new RegistrationCommand(new UserService())),
    REMOVE(new RemoveTrackCommand(new AdminService(), new CommonService())),
    SEARCH(new SearchCommand(new CommonService())),
    SET_LOCALE(new SetLocaleCommand()),
    SHOW_ALL_TRACKS(new UnsortedAllTrackCommand(new CommonService(), new CommandHandler<>())),
    SHOW_ALL_MUSICIANS(new UnsortedAllMusicianCommand(new CommonService(), new CommandHandler<>())),
    SHOW_ALL_PLAYLISTS(new UnsortedAllPlaylistCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_ID(new SortTrackIdCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_NAME(new SortTrackNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_GENRE(new SortTrackGenreCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_LENGTH(new SortTrackLengthCommand(new CommonService(), new CommandHandler<>())),
    SORT_MUSICIAN_BY_ID(new SortMusicianIdCommand(new CommonService(), new CommandHandler<>())),
    SORT_MUSICIAN_BY_NAME(new SortMusicianNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_GENRE_BY_ID(new SortGenreIdCommand(new CommonService(), new CommandHandler<>())),
    SORT_GENRE_BY_NAME(new SortGenreNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_PLAYLIST_BY_NAME(new SortPlaylistNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_PLAYLIST_BY_LENGTH(new SortPlaylistLengthCommand(new CommonService(), new CommandHandler<>())),
    SORT_PLAYLIST_BY_ID(new SortTrackGenreCommand(new CommonService(), new CommandHandler<>())),
    TO_CREATE_PLAYLIST(new ToCreatePlaylistCommand()),
    TO_REGISTR(new ToRegistrationCommand()),
    TO_REMOVE_TRACK(new ToRemoveTrackCommand(new CommonService(), new CommandHandler<>())),
    TO_LOGIN(new ToLoginCommand()),
    TO_REMOVE_MUSICIAN(new ToRemoveMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_REMOVE_GENRE(new ToRemoveMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_REMOVE_PLAYLIST(new ToRemoveMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_ADMIN(new ToAdminCommand()),
    TO_UPDATE_TRACK(new ToUpdateTrackCommand(new CommonService(), new CommandHandler<>())),
    TO_UPDATE_MUSICIAN(new ToUpdateMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_UPDATE_GENRE(new ToUpdateMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_UPDATE_PLAYLIST(new ToUpdateMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_UPLOAD_TRACK(new ToUploadTrackCommand()),
    TO_PROFILE(new ToProfileCommand()),
    TO_LIBRARY(new ToLibraryCommand()),
    TRACK_DETAIL(new TrackDetailCommand(new CommonService())),
    UPLOAD_TRACK(new UploadTrackCommand(new AdminService(), new CommonService())),
    UPDATE_TRACK(new UpdateTrackCommand(new AdminService(), new CommonService())),
    USER_PLAYLISTS(new UserPlaylistsCommand(new CommonService()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
     public Command getCommand() {
        return command;
     }
}
