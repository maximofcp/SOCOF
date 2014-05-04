package UI;

import SOCOF.Map;
import Util.Vector;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
*
* @author Filipe Paulo
*/
public class ControlPanel {

    private JPanel panel;
    private JButton startResumeButton;
    private JButton resetButton;
    private Map map;
    private MapMainUI mapFrame;
    private MapPanel mapPanel;

    public ControlPanel(MapMainUI mapFrame, Map map, MapPanel mapPanel) {
        this.mapFrame = mapFrame;
        this.map = map;
        this.mapPanel = mapPanel;
        createPartControl();
    }

    private void createPartControl() {
        panel = new JPanel();
        panel.setBackground(new Color(255,255,255));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        startResumeButton = new JButton("Start");

        resetButton = new JButton("Reset");
        resetButton.setEnabled(false);

        buttonPanel.add(startResumeButton);
        buttonPanel.add(resetButton);

        panel.add(buttonPanel);

        handleEvents(startResumeButton);
    }

    private void handleEvents(final JButton startResumeButton) {
        // Start/Resume button
        startResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (startResumeButton.getText().equalsIgnoreCase("Start")) {
                    processStartStatus(startResumeButton);
                } else if (startResumeButton.getText().equalsIgnoreCase("Pause")) {
                    processPauseStatus(startResumeButton);
                } else if (startResumeButton.getText().equalsIgnoreCase("Resume")) {
                    processResumeStatus(startResumeButton);
                }
            }

            private void processResumeStatus(JButton btn) {
                setRaceRunning();
                btn.setText("Pause");
            }

            private void processPauseStatus(JButton btn) {
                pauseRacing();
                btn.setText("Resume");
            }

            private void processStartStatus(JButton btn) {
                if (btn.getText().equalsIgnoreCase("Start")) {
                    setRaceRunning();
                }
                btn.setText("Pause");

                resetButton.setEnabled(true);
            }
        });

        // Reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRace();
                resetButton.setEnabled(false);
            }
        });

        mapPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("" + e.getPoint() + " long:" + Vector.getLongitude(e.getX()) +" lat:" + Vector.getLatitude(e.getY()));
            }

        });
    }

    private void setRace() {
        pauseRacing();
        map.reset();
        mapFrame.repaintMapPanel();
        startResumeButton.setText("Start");
    }

    private void setRaceRunning() {
        map.unpause();
    }

    private void pauseRacing() {
        map.pause();
    }

    public JPanel getControlPanel() {
        return this.panel;
    }
}
