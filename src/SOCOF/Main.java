package SOCOF;

import Utils.MathUtils;
import Utils.Vector;
import UI.MapMainUI;
import Utils.ManageConfigFiles;
import Utils.Rectangle;
import java.awt.Color;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
*
* @author Filipe Paulo
*/
public class Main {

    public static int frameWidth = 1000;		//largura
    public static int frameHeight = 1000;		//altura
    public static int carDimension = 5;			//dimensao dos carros
    public static int intervalCollsionDetection = 100;		//check interval

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException {

        Map map = new Map();
        ManageConfigFiles readFiles = new ManageConfigFiles(map);
        ArrayList<Car> cars = readFiles.readCarFile();
        ArrayList<Rectangle> rects = readFiles.readMapFile();

        map.setCars(cars);
        map.setRectangles(rects);
        // Map map=  readFiles.getMap();
//        Map map = new Map();
//        //caminho feito pelo carro, neste caso vai ser o mesmo para os 2 carros
//        Vector[] path = new Vector[]{new Vector(60d, 2.5d), new Vector(60d, 60d), new Vector(2d, 60d), new Vector(2.0d, 2.5d)};
//        Car car1 = new Car((short) 1, Color.BLUE, new Vector(2.0d, 2.0d), path, map);
//        car1.setVelocity(0.01d);
//        Car car2 = new Car((short) 2, Color.RED, new Vector(2.0d, 22.0d), path, map);
//        car2.setVelocity(0.01d);
//        map.addCar(car1);
//        map.addCar(car2);

        new MapMainUI(map, frameWidth, frameHeight);
        testeMath();
    }

    public static void testeMath() {
        double aux;
        aux = MathUtils.getVelocity(new Vector(16.63, 300.0), new Vector(33.3, 300.0));
        System.out.println("KM/H = " + (int) aux);
        int aux1 = MathUtils.closestPointOfApproach(new Vector(16.63, 300.0), new Vector(33.3, 300.0), new Vector(0.0, 0.0), new Vector(0.0, 0.0));
        System.out.println("t1 = " + (int) aux1);

        int aux2 = MathUtils.closestPointOfApproach(new Vector(200.0, 147.32), new Vector(200.0, 161.2), new Vector(16.63, 300.0), new Vector(33.3, 300.0));
        System.out.println("t2 = " + (int) aux2);

        Vector v = MathUtils.detectCollisionAt(aux1, new Vector(16.63, 300.0), new Vector(33.3, 300.0));
        System.out.println("P(11) = " + v);
        Vector v1 = MathUtils.detectCollisionAt(aux1, new Vector(200.0, 147.32), new Vector(200.0, 161.2));
        System.out.println("Q(11) = " + v1);
    }
}
