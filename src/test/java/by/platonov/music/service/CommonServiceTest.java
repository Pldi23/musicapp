package by.platonov.music.service;

import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class CommonServiceTest {

    private CommonService service;
    private User user;
    private Playlist playlist;

//    @Test
//    void createPlaylist() throws ServiceException, RepositoryException {
//        service = new CommonService();
//        user = User.builder().login("pldi").password("qwerty").admin(true).firstname("Dima").lastname("Platonov")
//                .birthDate(LocalDate.of(1986, 7, 2)).email("pldi@mail.ru")
//                .playlists(new HashSet<>())
//                .gender(User.Gender.MALE).active(false)
//                .photoPath("/usr/local/Cellar/tomcat/9.0.20/libexec/musicappfiles/photo/default_ava.png")
//                .verificationUuid(null).build();
//        //'pldi', 'qwerty', true, 'Dima', 'Platonov', 'pldi@mail.ru', '1986-07-02', true, false, null, null
//        String playlistName = "my";
//        service.createPlaylist(user, playlistName);
//
//        long actual = UserRepository.getInstance().query(new UserLoginSpecification(user.getLogin())).get(0).getPlaylists().size();
//        long expected = 2;
//        assertEquals(expected, actual);
//    }
}