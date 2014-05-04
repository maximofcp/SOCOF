package SOCOF;

import Util.Vector;

/**
*
* @author Filipe Paulo
*/
public class CarThread extends PauseThread implements Runnable {

    private Car car;

    public CarThread() {
    }

    public CarThread(Car c) {
        car = c;
    }

    @Override
    public void run() {
        while (true) {
            pausePoint();

            int nextDest = car.getNextDest();
            Vector dest = car.getDest();
            double distance = car.getPosition().distance(dest);
            car.setDistanceToDest(distance);

            if (distance <= 0.5d) {
                if (nextDest == car.getPath().length - 1) {
                    nextDest = 0;
                } else {
                    nextDest++;
                }
                car.setNextDest(nextDest);
            }
 
            if (!car.getPosition().equals(dest) && !isPause()) {
                car.moveAtDest(dest);
            }
        }
    }
}
