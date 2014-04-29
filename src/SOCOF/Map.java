package SOCOF;

import Utils.MathUtils;
import Utils.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;

/**
*
* @author Filipe Paulo
*/
public class Map extends Observable {

    private ArrayList<Car> cars;
    private ArrayList<Rectangle> rectangles;

    public Map() {
        this.cars = new ArrayList();
        this.rectangles = new ArrayList();
    }

    public void addCar(Car c) {
        this.getCars().add(c);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public int getCarsCount() {
        return this.getCars().size();
    }

    public void checkCollisionOnCar(Car VehicleA) {

        for (int i = 0; i < getCars().size(); i++) {
            Car VehicleB = getCars().get(i);
            if (VehicleA.getId() != VehicleB.getId()) {
                int secondsToCollision = MathUtils.closestPointOfApproach(VehicleA.getPrevPosition(), VehicleA.getPosition(), VehicleB.getPrevPosition(), VehicleB.getPosition());
                //verificar a colisao mais proxima
                if (secondsToCollision < 3 && secondsToCollision > 2) {
                    VehicleA.setState(Car.ActionState.decelerate);
                    System.out.println("COLISAO decelerate");
                } else if (secondsToCollision == 2) {
                    VehicleA.setState(Car.ActionState.stopping);
                    System.out.println("COLISAO stopping");
                } else {
                    VehicleA.setState(Car.ActionState.accelerate);
                    System.out.println("COLISAO accelerate");
                }
            }
        }
    }

    public void letNotify() {
        setChanged();
        notifyObservers();
    }

    public void pause() {
        for (Car c : getCars()) {
            c.pause();
        }
    }

    public void unpause() {
        for (Car c : getCars()) {
            c.unpause();
        }
    }

    public void reset() {
        for (Car c : getCars()) {
            c.reset();
        }
    }

    public void draw(Graphics g) {
        for (Car c : getCars()) {
            c.draw(g);
        }
        //notifica o observer (MapMainUI) que deve chamar o repaint()
        //isto server para a interface ficar sempre em loop a redesenhar os objetos 2d
        letNotify();
    }

    /**
     * @param cars the cars to set
     */
    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    /**
     * @return the rectangles
     */
    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    /**
     * @param rectangles the rectangles to set
     */
    public void setRectangles(ArrayList<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }
}
