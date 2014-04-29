package SOCOF;

import Utils.MathUtils;
import Utils.Vector;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Filipe Paulo
*/
public class Car {

    public enum ActionState {

        decelerate,
        accelerate,
        stopping,
        normal;
    }
    private short _id;
    private Map _map;
    private Vector _prevPosition;
    private Vector _position;
    private Vector _initPosition;
    private Vector[] _path;

    private final int STEP = 1;
    private double _distanceToDest;
    private int _nextDest;
    private ActionState _state;
    private double _velocity = 0.001d;
    private double _velMin = 0.001d;
    private double _velMax = 0.1d;
    private Color _color;
    private Thread[] _threads = new Thread[2];
    private PauseThread[] _pause = new PauseThread[2];

        public Car(short id, Color color, Vector[] path, Map map) {
        _id = id;
        _map = map;
        _color = color;
        _path = path;
        _position = path[0].clone();
        _prevPosition = _position.clone();
        _initPosition = _position.clone();
        _state = ActionState.normal;

        //isto td por um pause pff
        _pause[0] = new CollisionDetection(this);
        _pause[1] = new MovingCar(this);
        _threads[0] = new Thread((Runnable) _pause[0]);
        _threads[1] = new Thread((Runnable) _pause[1]);

        start();
    }
        
    public Car(short id, Color color, Vector pos, Vector[] path, Map map) {
        _id = id;
        _map = map;
        _color = color;
        _path = path;
        _position = pos;
        _prevPosition = pos.clone();
        _initPosition = pos.clone();
        _state = ActionState.normal;

        //isto td por um pause pff
        _pause[0] = new CollisionDetection(this);
        _pause[1] = new MovingCar(this);
        _threads[0] = new Thread((Runnable) _pause[0]);
        _threads[1] = new Thread((Runnable) _pause[1]);

        start();
    }

    public Car(short id, Color color, Vector pos, Vector[] path, ActionState s, Map map) {
        _id = id;
        _map = map;
        _color = color;
        _path = path;
        _position = pos;
        _prevPosition = pos.clone();
        _initPosition = pos.clone();
        _state = s;

        //isto td por um pause pff
        _pause[0] = new CollisionDetection(this);
        _pause[1] = new MovingCar(this);
        _threads[0] = new Thread((Runnable) _pause[0]);
        _threads[1] = new Thread((Runnable) _pause[1]);

        start();
    }

    public void start() {
        for (Thread t : _threads) {
            t.start();
        }
    }

    public void pause() {
        for (PauseThread t : _pause) {
            t.pause();
        }
    }

    public void unpause() {
        for (PauseThread t : _pause) {
            t.unpause();
        }
    }

    @Deprecated
    public Vector getNextPosition() {
        Vector dest = new Vector(60d, 2d);
        Vector next = _position;

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

            switch (_state) {
                //carro no estado normal. andar velocidade constante
                case normal:
                    move(dest);
                    System.out.println("Andar Normal");
                    break;
                //carro parado
                case stopping:
                    move(dest);
                    setVelocityToZero();
                    System.out.println("Parado");
                    break;
                // carro a aumentar a velocidade
                case accelerate:
                    move(dest);

                    setVelocityAdd(0.001d);
                    _state = Car.ActionState.normal;
                    System.out.println("Acelarar");
                    break;
                // carro a reduzir a velocidade
                case decelerate:
                    move(dest);
                    setVelocitySub(0.001d);
                    _state = Car.ActionState.normal;
                    System.out.println("Travar");
                    break;
            }

            Thread.sleep(STEP);		//1 unidade por segundo
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void move(Vector dest) {
        //guarda a ultima posicao
        _prevPosition.copy(_position);

        if (_position.equals(dest)) {
            return;
        }

        if (_position.getX() == dest.getX()) {
            if (_position.getY() == dest.getY()) {
            } else if (_position.getY() < dest.getY()) {
                if ((_position.getY() + getVelocity()) <= dest.getY()) {
                    _position.setY(_position.getY() + getVelocity());
                } else {
                    _position.setY(dest.getY());
                }
            } else {
                if ((_position.getY() - getVelocity()) >= dest.getY()) {
                    _position.setY(_position.getY() - getVelocity());
                } else {
                    _position.setY(dest.getY());
                }
            }
        } else {
            if (_position.getX() < dest.getX()) {
                if ((_position.getX() + getVelocity()) <= dest.getX()) {
                    _position.setX(_position.getX() + getVelocity());
                } else {
                    _position.setX(dest.getX());
                }
            } else {
                if ((_position.getX() - getVelocity()) >= dest.getX()) {
                    _position.setX(_position.getX() - getVelocity());
                } else {
                    _position.setX(dest.getX());
                }
            }
        }

        if (_position.getY() == dest.getY()) {
            if (_position.getX() == dest.getX()) {
            } else if (_position.getX() < dest.getX()) {
                if ((_position.getX() + getVelocity()) <= dest.getX()) {
                    _position.setX(_position.getX() + getVelocity());
                } else {
                    _position.setX(dest.getX());
                }
            } else {
                if ((_position.getX() - getVelocity()) >= dest.getX()) {
                    _position.setX(_position.getX() - getVelocity());
                } else {
                    _position.setX(dest.getX());
                }
            }
        } else {
            if (_position.getY() < dest.getY()) {
                if ((_position.getY() + getVelocity()) <= dest.getY()) {
                    _position.setY(_position.getY() + getVelocity());
                } else {
                    _position.setY(dest.getY());
                }
            } else {
                if ((_position.getY() - getVelocity()) >= dest.getY()) {
                    _position.setY(_position.getY() - getVelocity());
                } else {
                    _position.setY(dest.getY());
                }
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(_color);
        g2d.fill(new Rectangle2D.Double(_position.getX(), _position.getY(), Main.carDimension, Main.carDimension));
        Vector dest = getDest();
        g2d.setColor(Color.PINK);
        g2d.draw(new Line2D.Double(getPosition().getX(), getPosition().getY(), dest.getX(), dest.getY()));
    }

    public void checkCollision() {

        ArrayList<Car> cars = _map.getCars();
        for (int i = 0; i < cars.size(); i++) {
            Car VehicleB = cars.get(i);

            if (getId() != VehicleB.getId()) {
                //mesma direccao ou seja se vão para o mesmo ponto
                if (getDest().equals(VehicleB.getDest())) {

                    int secondsToCollision = MathUtils.closestPointOfApproach(getPrevPosition(), getPosition(), VehicleB.getPrevPosition(), VehicleB.getPosition());

                    //verificar a colisao mais proxima
                    if ((secondsToCollision <= 9 && secondsToCollision > 6) && getDistanceToDest() > VehicleB.getDistanceToDest()) {
                        setState(Car.ActionState.decelerate);
                        System.out.println("COLISAO decelerate");
                        return;
                       
                    } else if ((secondsToCollision <= 6 && secondsToCollision > 2) && getDistanceToDest() > VehicleB.getDistanceToDest()) {
                        setState(Car.ActionState.stopping);
                        System.out.println("COLISAO stopping");
                        return;
                        
                    } else if ((secondsToCollision >= 10) || (secondsToCollision <= 2)) {
                        setState(Car.ActionState.accelerate);
                        System.out.println("COLISAO accelerate");
                        // } else if ((secondsToCollision <= 1 && secondsToCollision > 0) && getDistanceToDest() > VehicleB.getDistanceToDest()) {
                    } else if (getPosition().distance(VehicleB.getPosition()) < 1.0d) {
                        System.out.println("Colideu");
                    }
                } else {
                    setState(Car.ActionState.accelerate);
                }
            }
        }

    }

    public void reset() {
        _state = ActionState.normal;
        _velocity = _velMin;
        _prevPosition.copy(_initPosition);
        _position.copy(_initPosition);
        _nextDest = 0;
    }

    // <editor-fold defaultstate="collapsed" desc="GETS/SETS">
    public Color getColor() {
        return _color;
    }

    public void setColor(Color color) {
        _color = color;
    }

    /**
     * @return the _id
     */
    public short getId() {
        return _id;
    }

    /**
     * @param id the _id to set
     */
    public void setId(short id) {
        _id = id;
    }

    /**
     * @return the _position
     */
    public Vector getPosition() {
        synchronized (_position) {
            return _position;
        }
    }

    /**
     * @param position the _position to set
     */
    public void setPosition(Vector position) {
        synchronized (_position) {
            _position = position;
        }
    }

    /**
     * @return the _prevPosition
     */
    public Vector getPrevPosition() {
        synchronized (_prevPosition) {
            return _prevPosition;
        }
    }

    /**
     * @param prevPosition the _prevPosition to set
     */
    public void setPrevPosition(Vector prevPosition) {
        synchronized (_prevPosition) {
            _prevPosition = prevPosition;
        }
    }

    /**
     * @return the _state
     */
    public ActionState getState() {
        return _state;
    }

    /**
     * @param state the _state to set
     */
    public void setState(ActionState state) {
        _state = state;
    }

    /**
     * @return the _velocity
     */
    public double getVelocity() {
        return MathUtils.round(_velocity);
    }

    /**
     * @param velocity the _velocity to set
     */
    public void setVelocity(double velocity) {
        velocity = MathUtils.round(velocity);
        if (velocity < _velMin || velocity > _velMax) {
            return;
        }
        _velocity = velocity;
    }

    public void setVelocityToZero() {
        _velocity = 0;
    }

    public void setVelocityAdd(double addvelocity) {
        addvelocity = MathUtils.round(addvelocity);
        if ((_velocity + addvelocity) > _velMax) {
            return;
        }
        _velocity = MathUtils.round(_velocity + addvelocity);
    }

    public void setVelocitySub(double subvelocity) {
        subvelocity = MathUtils.round(subvelocity);
        if ((_velocity - subvelocity) < _velMin) {
            return;
        }
        _velocity = MathUtils.round(_velocity - subvelocity);
    }

    /**
     * @return the _path
     */
    public Vector[] getPath() {
        return _path;
    }

    /**
     * @param path the _path to set
     */
    public void setPath(Vector[] path) {
        _path = path;
    }

    public double getVelocityKM() {
        return MathUtils.getVelocity(getPrevPosition(), getPosition()) * Main.carDimension;
    }

    public Vector getDest() {
        return getPath()[_nextDest];
    }

    /**
     * @return the _distanceToNextDest
     */
    public double getDistanceToDest() {
        return MathUtils.round(_distanceToDest);
    }

    /**
     * @param distanceToDest the _distanceToDest to set
     */
    public void setDistanceToDest(double distanceToDest) {
        _distanceToDest = MathUtils.round(distanceToDest);
    }

    /**
     * @return the nextDest
     */
    public int getNextDest() {
        return _nextDest;
    }

    /**
     * @param nextDest the nextDest to set
     */
    public void setNextDest(int nextDest) {
        _nextDest = nextDest;
    }

// </editor-fold>
    @Override
    public final String toString() {
        return String.format("Position: %s with State: %s", _position, _state);
    }
}
