package Utils;

import SOCOF.Main;
import org.w3c.dom.ranges.RangeException;

/**
*
* @author Filipe Paulo
*/
public class Vector {

    private double x;
    private double y;

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(double coodx, double coody) {
        x = MathUtils.round(coodx);
        y = MathUtils.round(coody);
    }

    public Vector(int longitude, int latitude) {
        if ((longitude >= 0 && longitude <= 180) && (longitude >= 0 && longitude <= 180)) {
            x = MathUtils.round(longitude * (Main.frameWidth / 180));//0-180
            y = MathUtils.round(latitude * (Main.frameHeight / 90));//0-90
        } else {
            throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "Invalid values out of range");
        }

    }
    
    public static int getLongitude(double x) {
        return (int) (x * 180) / Main.frameWidth;
    }

    public static int getLatitude(double y) {
        return (int) (y * 90) / Main.frameHeight;
    }
    public void setLongitude(int longitude) {
        x = MathUtils.round(longitude * (Main.frameWidth / 180));//0-180
    }

    public void setLatitude(int latitude) {
        y = MathUtils.round(latitude * (Main.frameHeight / 90));//0-90
    }

    public Vector sub(Vector v1) {
        return new Vector(v1.x - x, v1.y - y);
    }

    /**
     * Compute the dot product between and current and given vector.
     *
     * @param v input vector
     * @return the dot product
     */
    public Vector mul(Vector v) {
        return new Vector(x * v.x, y * v.y);
    }

    public Vector mul(float v) {
        return new Vector(x * v, y * v);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public final float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public final double lengthSquared() {
        return (x * x) + (y * y);
    }

    public final Vector negate() {
        return new Vector(-x, -y);
    }

    public final Vector div(Vector v1) {

        return new Vector(x / v1.x, y / v1.y);
    }

    public final double dot(Vector v1) {
        return (x * v1.x) + (y * v1.y);
    }

    public double getAngle(Vector dest) {
        return MathUtils.round(Math.atan2(dest.y - y, dest.x - x));
    }

    /**
     * Calcula a distancia entre o vector atual com o vector de destino
     *
     * @param dest Vector de destino
     * @return distancia do vector atual com o vector dest
     */
    public double distance(Vector dest) {
        double x = dest.getX() - getX();
        double y = dest.getY() - getY();

        return Math.sqrt((x * x) + (y * y));
    }

    /**
     * @return the x
     */
    public double getX() {
        return MathUtils.round(x);
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = MathUtils.round(x);
    }

    /**
     * @return the y
     */
    public double getY() {
        return MathUtils.round(y);
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = MathUtils.round(y);
    }

    public void copy(Vector v) {
        x = v.getX();
        y = v.getY();
    }

    @Override
    public Vector clone() {
        return new Vector(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        Vector other = ((Vector) obj);
        return getX() == other.getX() && getY() == other.getY();
    }

    @Override
    public final String toString() {
        return String.format("(%.5f, %.5f)", x, y);
    }
}
