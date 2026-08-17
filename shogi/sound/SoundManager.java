package sound;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundManager {

    private Sound moveSound;
    private Sound captureSound;
    private Sound promoteSound;
    private Sound dropSound;
    private Sound checkSound;
    private Sound gameEndSound;

    public void init() {
        TinySound.init();
        moveSound    = TinySound.loadSound("move.wav");
        captureSound = TinySound.loadSound("capture.wav");
        promoteSound = TinySound.loadSound("promote.wav");
        dropSound    = TinySound.loadSound("drop.wav");
        checkSound   = TinySound.loadSound("check.wav");
        gameEndSound = TinySound.loadSound("gameend.wav");
    }

    public void playMove()    { if (moveSound    != null) moveSound.play();    }
    public void playCapture(boolean isCapture) {
        if (isCapture) {
            if (captureSound != null) captureSound.play();
        } else {
            if (moveSound != null) moveSound.play();
        }
    }
    public void playPromote() { if (promoteSound != null) promoteSound.play(); }
    public void playDrop()    { if (dropSound    != null) dropSound.play();    }
    public void playCheck()   { if (checkSound   != null) checkSound.play();   }
    public void playGameEnd() { if (gameEndSound != null) gameEndSound.play(); }

    public void shutdown() {
        TinySound.shutdown();
    }
}