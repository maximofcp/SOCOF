package SOCOF;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Util.MathUtils;
import Util.Vector;

/**
*
* @author Filipe Paulo
*/
public class Car {

    public enum ActionState {

        DECELERATE,
        ACCELERATE,
        STOP,
        NORMAL;
    }
    private short id;
    private Map map;
    private Vector previousPosition;
    private Vector position;
    private Vector initPosition;
    private Vector[] path;

    private final int STEP = 1;
    private double distanceToDest;
    private int nextDest;
    private ActionState state;
    private double velocity = 0.001d;
    private double minVel = 0.001d;
    private double maxVel = 0.1d;
    private Color color;
    private Thread[] threads = new Thread[2];
    private PauseThread[] pause = new PauseThread[2];

        public Car(short id, Color color, Vector[] path, Map map) {
        this.id = id;
        this.map = map;
        this.color = color;
        this.path = path;
        this.position = path[0].clone();
        this.previousPosition = this.position.clone();
        this.initPosition = this.position.clone();
        this.state = ActionState.NORMAL;
        pause[0] = new CollisionDetection(this);
        pause[1] = new CarThread(this);
        threads[0] = new Thread((Runnable) pause[0]);
        threads[1] = new Thread((Runnable) pause[1]);

        start();
    }
        
    public Car(short id, Color color, Vector pos, Vector[] path, Map map) {
        this.id = id;
        this.map = map;
        this.color = color;
        this.path = path;
        this.position = pos;
        this.previousPosition = pos.clone();
        this.initPosition = pos.clone();
        this.state = ActionState.NORMAL;
        pause[0] = new CollisionDetection(this);
        pause[1] = new CarThread(this);
        threads[0] = new Thread((Runnable) pause[0]);
        threads[1] = new Thread((Runnable) pause[1]);

        start();
    }

    public Car(short id, Color color, Vector pos, Vector[] path, ActionState s, Map map) {
        this.id = id;
        this.map = map;
        this.color = color;
        this.path = path;
        this.position = pos;
        this.previousPosition = pos.clone();
        this.initPosition = pos.clone();
        this.state = s;
        pause[0] = new CollisionDetection(this);
        pause[1] = new CarThread(this);
        threads[0] = new Thread((Runnable) pause[0]);
        threads[1] = new Thread((Runnable) pause[1]);

        start();
    }

    public void start() {
        for (Thread t : threads) {
            t.start();
        }
    }

    public void pause() {
        for (PauseThread t : pause) {
            t.pause();
        }
    }

    public void unpause() {
        for (PauseThread t : pause) {
            t.unpause();
        }
    }

    public Vector getNextPosition() {
        Vector dest = new Vector(60d, 2d);
        Vector next = position;

        if (next.getX() != dest.getX() && next.getY() != dest.getY()) {

            if (next.getX() < dest.getX()) {
                next.setX(next.getX() + getVelocity());
            } else {
                next.setX(next.getX() - getVelocity());
            }
            if (next.getY() < dest.getY()) {
                next.setY(next.getY() + getVelocity());
            } else {
                next.setY(next.getY() - getVelocity());
            }

        } else {
            if (next.getX() == dest.getX()) {
                if (next.getY() < dest.getY()) {
                    next.setY(next.getY() + getVelocity());
                } else {
                    next.setY(next.getY() - getVelocity());
                }
            }
            if (next.getY() == dest.getY()) {
                if (next.getX() < dest.getX()) {
                    next.setX(next.getX() + getVelocity());
                } else {
                    next.setX(next.getX() - getVelocity());
                }
            }
        }
        return next;
    }

    public void moveAtDest(Vector dest) {

        try {

            switch (state) {

            	// Normal state. Constant speed
                case NORMAL:
                    move(dest);
                    break;

                // Stop state
                case STOP:
                    move(dest);
                    stop();
                    break;

                // Accelerating state
                case ACCELERATE:
                    move(dest);
                    increaseVelocity(0.001d);
                    state = Car.ActionState.NORMAL;
                    break;

                // Decelerating state
                case DECELERATE:
                    move(dest);
                    decreaseVelocity(0.001d);
                    state = Car.ActionState.NORMAL;
                    break;
            }

            Thread.sleep(STEP);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void move(Vector dest) {

    	// Saves previous position
        previousPosition.copy(position);

        if (position.equals(dest)) {
            return;
        }

        if (position.getX() == dest.getX()) {
            if (position.getY() == dest.getY()) {

            } else if (position.getY() < dest.getY()) {
                if ((position.getY() + getVelocity()) <= dest.getY()) {
                    position.setY(position.getY() + getVelocity());
                } else {
                    position.setY(dest.getY());
                }
            } else {
                if ((position.getY() - getVelocity()) >= dest.getY()) {
                    position.setY(position.getY() - getVelocity());
                } else {
                    position.setY(dest.getY());
                }
            }
        } else {
            if (position.getX() < dest.getX()) {
                if ((position.getX() + getVelocity()) <= dest.getX()) {
                    position.setX(position.getX() + getVelocity());
                } else {
                    position.setX(dest.getX());
                }
            } else {
                if ((position.getX() - getVelocity()) >= dest.getX()) {
                    position.setX(position.getX() - getVelocity());
                } else {
                    position.setX(dest.getX());
                }
            }
        }

        if (position.getY() == dest.getY()) {
            if (position.getX() == dest.getX()) {
            } else if (position.getX() < dest.getX()) {
                if ((position.getX() + getVelocity()) <= dest.getX()) {
                    position.setX(position.getX() + getVelocity());
                } else {
                    position.setX(dest.getX());
                }
            } else {
                if ((position.getX() - getVelocity()) >= dest.getX()) {
                    position.setX(position.getX() - getVelocity());
                } else {
                    position.setX(dest.getX());
                }
            }
        } else {
            if (position.getY() < dest.getY()) {
                if ((position.getY() + getVelocity()) <= dest.getY()) {
                    position.setY(position.getY() + getVelocity());
                } else {
                    position.setY(dest.getY());
                }
            } else {
                if ((position.getY() - getVelocity()) >= dest.getY()) {
                    position.setY(position.getY() - getVelocity());
                } else {
                    position.setY(dest.getY());
                }
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fill(new Rectangle2D.Double(position.getX(), position.getY(), Main.carDimension, Main.carDimension));
        Vector dest = getDest();
        g2d.setColor(Color.PINK);
        g2d.draw(new Line2D.Double(getPosition().getX(), getPosition().getY(), dest.getX(), dest.getY()));
    }

    public void checkCollision() {

        ArrayList<Car> cars = map.getCars();
        for (Car carX : cars) {
        	
            if (getId() != carX.getId()) {

                if (getDest().equals(carX.getDest())) {

                    int secondsToCollision = MathUtils.closestPoint(this.getPrevPosition(), this.getPosition(), carX.getPrevPosition(), carX.getPosition());

                    if ((600 < secondsToCollision && secondsToCollision <= 900) && this.getDistanceToDest() > carX.getDistanceToDest()) {
                        this.setState(ActionState.DECELERATE);
                        
                        if(this.getPosition().getX() - this.getPrevPosition().getX() == 0 ){
                            this.setPosition(new Vector(this.getPosition().getX() + this.getVelocity(), this.getPosition().getY()));
                        }
                        
                        if(this.getPosition().getY() - this.getPrevPosition().getY() == 0 ){
                            this.setPosition(new Vector(this.getPosition().getX(), this.getPosition().getY() + this.getVelocity() ));
                        }

                        return;
                        
                    } else if ((200 < secondsToCollision && secondsToCollision <= 600) && this.getDistanceToDest() > carX.getDistanceToDest()) {
                        this.setState(ActionState.STOP);
                        return;
                    } else if ((secondsToCollision >= 1000) || (secondsToCollision <= 200)) {
                        this.setState(ActionState.ACCELERATE);
                        
                    } else if (getPosition().distance(carX.getPosition()) < 1.0d) {
                        System.out.println("Colidiu");
                    }
                } else {
                	
                    this.setState(ActionState.ACCELERATE);
                }
            }
        }

    }

    public void reset() {
        state = ActionState.NORMAL;
        velocity = minVel;
        previousPosition.copy(initPosition);
        position.copy(initPosition);
        nextDest = 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public Vector getPosition() {
        synchronized (position) {
            return position;
        }
    }

    public void setPosition(Vector position) {
        synchronized (position) {
            this.position = position;
        }
    }

    public Vector getPrevPosition() {
        synchronized (previousPosition) {
            return previousPosition;
        }
    }

    public void setPrevPosition(Vector prevPosition) {
        synchronized (previousPosition) {
        	this.previousPosition = prevPosition;
        }
    }

    public ActionState getState() {
        return state;
    }

    public void setState(ActionState state) {
        this.state = state;
    }

    public double getVelocity() {
        return MathUtils.round(velocity);
    }

    public void setVelocity(double velocity) {
        velocity = MathUtils.round(velocity);
        if (velocity < minVel || velocity > maxVel) {
            return;
        }
        this.velocity = velocity;
    }

    public void stop() {
        velocity = 0;
    }

    public void increaseVelocity(double v) {
        v = MathUtils.round(v);
        if ((velocity + v) > maxVel) {
            return;
        }
        velocity = MathUtils.round(velocity + v);
    }

    public void decreaseVelocity(double v) {
        v = MathUtils.round(v);
        if ((velocity - v) < maxVel) {
            return;
        }
        velocity = MathUtils.round(velocity - v);
    }

    public Vector[] getPath() {
        return path;
    }

    public void setPath(Vector[] path) {
        this.path = path;
    }

    public double getVelocityKM() {
        return MathUtils.getVelocity(getPrevPosition(), getPosition()) * Main.carDimension;
    }

    public Vector getDest() {
        return getPath()[nextDest];
    }

    public double getDistanceToDest() {
        return MathUtils.round(distanceToDest);
    }

    public void setDistanceToDest(double distanceToDest) {
        this.distanceToDest = MathUtils.round(distanceToDest);
    }

    public int getNextDest() {
        return nextDest;
    }

    public void setNextDest(int nextDest) {
        this.nextDest = nextDest;
    }

}
