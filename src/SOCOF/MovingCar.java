package SOCOF;

import Utils.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Filipe Paulo
*/
public class MovingCar extends PauseThread implements Runnable {

    private Car car;

    public MovingCar() {
    }

    public MovingCar(Car c) {
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
            //verificar a distancia ate a ponto que estamos a movimentar nos
            //se for menor que 0.5 entao passamos para o proximo ponto(nextDest)
            if (distance <= 0.5d) {
                if (nextDest == car.getPath().length - 1) {
                    nextDest = 0;
                } else {
                    nextDest++;
                }
                car.setNextDest(nextDest);
            }
            //mover apenas 1 unidade de cada vez      
            if (!car.getPosition().equals(dest) && !isPause()) {
                car.moveAtDest(dest);
            }
        }
    }
}
