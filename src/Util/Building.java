package Util;

import java.awt.Color;

/**
*
* @author Filipe Paulo
*/
public class Building {
	
    private Color color;
    private double x,y;
    private int width, height;

    
    public Building(Color c, double x, double y, int width, int height){
        this.color=c;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
   
}
