package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import SOCOF.Map;
import Util.Building;

/**
*
* @author Filipe Paulo
*/
@SuppressWarnings("serial")
public class MapPanel extends JPanel {

    private Map map;
    private int mapWidth, mapHeight;
    private ArrayList<Building> buildings;

    // Note: get map width and height only after the repaint()
    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight() {
        return this.mapHeight;
    }

    public MapPanel(Map m) {
        map = m;
        buildings = map.getBuildings();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawMap(g);
        map.draw(g);
        this.mapWidth = this.getWidth();
        this.mapHeight = this.getHeight();
    }

    public void mouseActions(final Rectangle2D rect) {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (rect.contains(e.getPoint())) {
                    setToolTipText("Inside rect");
                } else {
                    setToolTipText("Outside rect");
                }
                ToolTipManager.sharedInstance().mouseMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub  
            }
        });
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private void drawMap(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Building r : buildings) {
            Rectangle2D rect = new Rectangle2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            //g2d.setColor(r.getColor());
            g2d.setColor(Color.BLUE);
            g2d.fill(rect);
            g2d.setColor(Color.darkGray);
            g2d.draw(rect);
        }
    }
}
