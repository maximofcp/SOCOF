package SOCOF;

import Util.Building;
import Util.MathUtils;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;

/**
*
* @author Filipe Paulo
*/
public class Map extends Observable {

    private ArrayList<Car> cars;
    private ArrayList<Building> buildings;

    public Map() {
        this.cars = new ArrayList<Car>();
        this.buildings = new ArrayList<Building>();
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

    public void checkCollisionOnCar(Car carX) {

        for (int i = 0; i < getCars().size(); i++) {
            Car carY = getCars().get(i);
            
            if (carX.getId() != carY.getId()) {
            	
                int secondsToCollision = MathUtils.closestPoint(carX.getPrevPosition(), carX.getPosition(), carY.getPrevPosition(), carY.getPosition());
                if (secondsToCollision < 3 && secondsToCollision > 2) {
                	carX.setState(Car.ActionState.DECELERATE);

                } else if (secondsToCollision == 2) {
                	carX.setState(Car.ActionState.STOP);

                } else {
                	carX.setState(Car.ActionState.ACCELERATE);

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
        letNotify();
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
}
