
package Util;

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
public class DataManager {

    private ArrayList<Car> cars;
    private ArrayList<Building> buildings;
    private static final String CAR_FILE = "cars.txt";
    private static final String MAP_FILE = "buildings.txt";
    
    private Map map;

    public DataManager(Map m) {
        map = m;
    }

    public ArrayList<Car> readCarFile() {
        cars = new ArrayList<Car>();
        buildings = new ArrayList<Building>();

        try {
            File file = new File(CAR_FILE);
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

    public ArrayList<Building> readMapFile() {

        try {
            File file = new File(MAP_FILE);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Building b;
            while ((line = bufferedReader.readLine()) != null) {

                String[] lineArray = line.split(",");
                if (lineArray.length == 4) {
                    String[] coord = lineArray[0].split(";");
                    int width = Integer.parseInt(lineArray[1]);
                    int height = Integer.parseInt(lineArray[2]);

                    b = new Building(Color.BLUE, Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), width, height);
                    buildings.add(b);
                }

            }
            fileReader.close();

        } catch (IOException e) {
        } catch (Exception e) {
        }
        return buildings;
    }

    public ArrayList<Car> getCars() {
        return cars;
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
