
package Utils;

import SOCOF.Car;
import SOCOF.Map;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Filipe Paulo
 */
public class ManageConfigFiles {

    private ArrayList<Car> cars;
    private ArrayList<Rectangle> rectangles;
    private Map map;

    public ManageConfigFiles(Map m) {
        map = m;
    }

    public ArrayList<Car> readCarFile() {
        cars = new ArrayList();
        rectangles = new ArrayList();

        try {
            File file = new File("carros.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Car c;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineArray = line.split(",");
                if (lineArray.length > 3) {
                    short id = Short.parseShort(lineArray[0]);
                    String[] colors = lineArray[1].split(";");
                    Color color = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
                    Vector[] vecs = new Vector[lineArray.length - 2];
                    for (int i = 0; i < vecs.length; i++) {
                        String[] vect = lineArray[2 + i].split(";");
                        vecs[i] = new Vector(Integer.parseInt(vect[0]), Integer.parseInt(vect[1]));
                    }
                    c = new Car(id, color, vecs, map);
                    cars.add(c);
                }
            }
            fileReader.close();
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return cars;
    }

    public ArrayList<Rectangle> readMapFile() {

        try {
            File file = new File("mapa.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Rectangle rec;
            while ((line = bufferedReader.readLine()) != null) {

                String[] lineArray = line.split(",");
                if (lineArray.length == 4) {
                    String[] coord = lineArray[0].split(";");
                    int width = Integer.parseInt(lineArray[1]);
                    int height = Integer.parseInt(lineArray[2]);
                    String[] colors = lineArray[3].split(";");
                    Color color = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));

                    rec = new Rectangle(color, Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), width, height);
                    rectangles.add(rec);
                }

            }
            fileReader.close();

        } catch (IOException e) {
        } catch (Exception e) {
        }
        return rectangles;
    }

    /**
     * @return the cars
     */
    public ArrayList<Car> getCars() {
        return cars;
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
