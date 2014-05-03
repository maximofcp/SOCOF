package SOCOF;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Filipe Paulo
*/
public class CollisionDetection extends PauseThread implements Runnable {

    private Car car;

    public CollisionDetection() {
    }

    public CollisionDetection(Car c) {
        car = c;
    }

    @Override
    public void run() {

        while (true) {
            try {
                pausePoint();
                
                car.checkCollision();
                Thread.sleep(Main.intervalCollsionDetection);
            } catch (InterruptedException ex) {
                Logger.getLogger(CollisionDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
