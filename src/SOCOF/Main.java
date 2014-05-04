package SOCOF;

import UI.MapMainUI;
import Util.Building;
import Util.DataManager;

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

    public static void main(String[] args){

    	// Starts map
        Map map = new Map();
        DataManager data = new DataManager(map);
        
        // Reads cars and buildings from the file
        ArrayList<Car> cars = data.readCarFile();
        ArrayList<Building> rects = data.readMapFile();

        // Adds cars and buildings to the map
        map.setCars(cars);
        map.setBuildings(rects);

        // Creates UI
        new MapMainUI(map, frameWidth, frameHeight);
    }
}
