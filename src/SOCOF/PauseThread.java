package SOCOF;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Filipe Paulo
*/
public class PauseThread {

    private boolean needToPause = true;

    public synchronized void pausePoint() {
        while (needToPause) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(PauseThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void pause() {
        needToPause = true;
    }

    public synchronized void unpause() {
        if (needToPause) {
            needToPause = false;
            this.notifyAll();
        }
    }

    public boolean isPause() {
        return needToPause;
    }
}
