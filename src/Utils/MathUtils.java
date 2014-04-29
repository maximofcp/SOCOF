package Utils;

import SOCOF.Main;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
*
* @author Filipe Paulo
*/
public class MathUtils {

    /**
     * Calculate the distance the first vehicle has travelled from the Position
     * v1 to Position v2
     *
     * @param v1
     * @param v2
     * @return approximately KM/H
     */
    public static double getVelocity(Vector v1, Vector v2) {
        return round(Math.sqrt(Math.pow((v2.getX() - v1.getX()), 2) + Math.pow((v2.getY() - v1.getY()), 2)) * 3.6);
    }

    public static Vector detectCollisionAt(int secs, Vector v1, Vector v2) {
        Vector u = v1.sub(v2);
        return v1.add((u.mul(secs)));
    }

    public static int closestPointOfApproach(Vector v1VehicleA, Vector v2VehicleA, Vector v1VehicleB, Vector v2VehicleB) {

        Vector u = v1VehicleA.sub(v2VehicleA);
        Vector v = v1VehicleB.sub(v2VehicleB);
        Vector w0 = v1VehicleA.sub(v1VehicleB).negate();

        double a = w0.dot(u.sub(v));
        double b = Math.pow((Math.sqrt(Math.pow(u.getX() + u.getY(), 2) + Math.pow(v.getX() + v.getY(), 2))), 2);
        return (int) (a / b);
    }

    public static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(5, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
