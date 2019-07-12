package by.platonov.music.util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-30.
 * @version 0.0.1
 */
@Log4j2
public class MusicPlayer extends Thread {

    private String path;
    private int pausedOnFrame = 0;

    public MusicPlayer(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        play();
    }

    public void play() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            AdvancedPlayer player = new AdvancedPlayer(fileInputStream);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    pausedOnFrame = evt.getFrame();
                }
            });
            log.debug("playing");
            player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            log.error(path + "file could not be loaded ", e);
        }
    }
}
