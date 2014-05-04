package SOCOF;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Filipe Paulo
*/
public class PauseThread {

    private boolean pause = true;

    public synchronized void pausePoint() {
        while (pause) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(PauseThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized void pause() {
        pause = true;
    }

    public synchronized void unpause() {
        if (pause) {
        	pause = false;
            this.notifyAll();
        }
    }

    public boolean isPause() {
        return pause;
    }
}
