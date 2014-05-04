package UI;

import SOCOF.Map;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
*
* @author Filipe Paulo
*/
public class MapMainUI implements Observer {

    private JFrame raceFrame;
    private MapPanel mapPanel;
    private ControlPanel controlPanel;

    private Map map;

    public MapMainUI(Map m, int w, int h) {
        this.map = m;
        mapPanel = new MapPanel(map);
        controlPanel = new ControlPanel(this, map, mapPanel);

        raceFrame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(mapPanel, BorderLayout.CENTER);
        panel.add(controlPanel.getControlPanel(), BorderLayout.SOUTH);

        raceFrame.add(panel);
        raceFrame.setTitle("Cooperative Collision Warning System \n" + "System");

        raceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        raceFrame.pack();
        raceFrame.setSize(w, h+50);
        raceFrame.setLocationRelativeTo(null);
        raceFrame.setResizable(false);
        raceFrame.setVisible(true);
        map.addObserver(this);
    }

    public JFrame getFrame() {
        return raceFrame;
    }

    public void repaintMapPanel() {
        mapPanel.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        repaintMapPanel();
    }

}
