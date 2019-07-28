package by.platonov.music.command;

import by.platonov.music.command.impl.*;
import by.platonov.music.service.*;
import by.platonov.music.service.FileService;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public enum CommandType {

    ACTIVATION(new ActivationCommand(new UserService())),
    ADD_TRACK_TO_PLAYLIST(new AddTrackToPlaylistCommand(new CommonService())),
    CHANGE_ACCESS(new ChangePlaylistAccessCommand(new UserService(), new CommonService())),
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserService())),
    ENTRY(new EntryCommand(new CommonService())),
    ERROR(new ErrorCommand()),
    FILTER_TRACK(new FilterTrackCommand(new CommonService(), new CommandHandler<>())),
    FILTER_USER(new FilterUserCommand(new UserService(), new CommandHandler<>())),
    LOGIN(new LoginCommand(new UserService(), new CommonService())),
    LOGOUT(new LogoutCommand()),
    MUSICIAN_DETAIL(new MusicianDetailCommand(new CommonService())),
    PLAYLIST_DETAIL(new PlaylistDetailCommand(new CommonService())),
    PLAYLIST_CREATE(new PlaylistCreateCommand(new CommonService())),
    QUERY(new QueryCommand(new CommonService())),
    REGISTER(new RegistrationCommand(new UserService(), false)),
    REGISTER_ADMIN(new RegistrationCommand(new UserService(), true)),
    REMOVE(new RemoveTrackCommand(new AdminService(), new CommonService(), new FileService())),
    REMOVE_CANCEL(new RemoveCancelCommand()),
    REMOVE_MY_PLAYLIST(new RemoveMyPlaylistCommand(new CommonService())),
    REMOVE_TRACK_FROM_PLAYLIST(new RemoveTrackFromPlaylistCommand(new CommonService())),
    SEARCH(new SearchCommand(new CommonService())),
    SET_LOCALE(new SetLocaleCommand(new CommonService())),
    SHOW_ALL_TRACKS(new UnsortedAllTrackCommand(new CommonService(), new CommandHandler<>())),
    SHOW_ALL_MUSICIANS(new UnsortedAllMusicianCommand(new CommonService(), new CommandHandler<>())),
    SHOW_ALL_PLAYLISTS(new UnsortedAllPlaylistCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_ID(new SortTrackIdCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_NAME(new SortTrackNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_GENRE(new SortTrackGenreCommand(new CommonService(), new CommandHandler<>())),
    SORT_TRACK_BY_LENGTH(new SortTrackLengthCommand(new CommonService(), new CommandHandler<>())),
    SORT_MUSICIAN_BY_ID(new SortMusicianIdCommand(new CommonService(), new CommandHandler<>())),
    SORT_MUSICIAN_BY_NAME(new SortMusicianNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_PLAYLIST_BY_NAME(new SortPlaylistNameCommand(new CommonService(), new CommandHandler<>())),
    SORT_PLAYLIST_BY_LENGTH(new SortPlaylistLengthCommand(new CommonService(), new CommandHandler<>())),
    SORT_PLAYLIST_BY_ID(new SortPlaylistIdCommand(new CommonService(), new CommandHandler<>())),
    TO_ADMIN(new ToAdminCommand(new CommonService())),
    TO_CREATE_PLAYLIST(new ToCreatePlaylistCommand()),
    TO_LOGIN(new ToLoginCommand()),
    TO_LIBRARY(new ToLibraryCommand(new CommonService())),
    TO_PROFILE(new ToProfileCommand()),
    TO_REGISTR(new ToRegistrationCommand()),
    TO_REGISTER_ADMIN(new ToAdminRegistrationCommand()),
    TO_REMOVE_TRACK(new ToRemoveTrackCommand(new CommonService(), new CommandHandler<>())),
    TO_REMOVE_MUSICIAN(new ToRemoveMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_REMOVE_GENRE(new ToRemoveMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_REMOVE_PLAYLIST(new ToRemovePlaylistCommand(new CommonService(), new CommandHandler<>())),
    TO_UPDATE_TRACK(new ToUpdateTrackCommand(new CommonService(), new CommandHandler<>())),
    TO_UPDATE_MUSICIAN(new ToUpdateMusicianCommand(new CommonService(), new CommandHandler<>())),
    TO_UPDATE_PLAYLIST(new ToUpdatePlaylistCommand(new CommonService(), new CommandHandler<>())),
    TO_UPLOAD_TRACK(new ToUploadTrackCommand()),
    TO_USER_LIBRARY(new ToUserLibraryCommand(new UserService(), new CommandHandler<>())),
    TRACK_DETAIL(new TrackDetailCommand(new CommonService())),
    UPLOAD_TRACK(new UploadTrackCommand(new AdminService(), new CommonService(), new FileService())),
    UPLOAD_IMG(new UploadImageCommand(new UserService(), new FileService())),
    UPDATE_BIRTHDATE(new UpdateBirthDateCommand(new UserService(), new CommandHandler())),
    UPDATE_GENDER(new UpdateGenderCommand(new UserService(), new CommandHandler())),
    UPDATE_FIRSTNAME(new UpdateFirstnameCommand(new UserService(), new CommandHandler())),
    UPDATE_LASTNAME(new UpdateLastnameCommand(new UserService(), new CommandHandler())),
    UPDATE_MUSICIAN(new UpdateMusicianCommand(new AdminService())),
    UPDATE_PLAYLIST(new UpdatePlaylistCommand(new AdminService(), new CommonService())),
    UPDATE_TRACK(new UpdateTrackCommand(new AdminService(), new CommonService())),
    USER_DETAIL(new UserDetailCommand(new UserService())),
    USER_PLAYLISTS(new UserPlaylistsCommand(new CommonService()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
     public Command getCommand() {
        return command;
     }
}
