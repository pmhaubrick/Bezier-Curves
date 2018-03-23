package delegateUI;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class UserInputListeners extends MouseAdapter implements ActionListener {

    private Panel panel;
    private String[] cpNumStrings = { "2", "3", "4" };
    private JComboBox<String> cpNumBox = new JComboBox<String>(cpNumStrings);
    private JLabel cpNumText = new JLabel("No. of Control Points:");
    private JLabel curvePrecText = new JLabel("       Sample Frequency as a % (between 0-1):");
    private JTextField curvePrecTF = new JTextField("0.05", 5);
    private JButton selectPrec = new JButton("Confirm");
    private JLabel lightText = new JLabel("       Toggle switch for placing point light:");
    private JButton lightButton = new JButton("Select a light position");
    private boolean lightPressed = false;


    public UserInputListeners(Panel pan) {
        super();
        panel = pan;
        cpNumBox.setSelectedIndex(1);
        cpNumBox.addActionListener(this);
        selectPrec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tIntvl = curvePrecTF.getText();
                double tInterval = Double.parseDouble(tIntvl);
                panel.setTIncrement(tInterval);
            }
        });
        lightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lightPressed) {
                    lightPressed = false;
                    lightButton.setText("Select a light position");
                } else {
                    lightPressed = true;
                    lightButton.setText("Return to drawing curves");
                }
            }
        });
        panel.add(cpNumText);
        panel.add(cpNumBox);
        panel.add(curvePrecText);
        panel.add(curvePrecTF);
        panel.add(selectPrec);
        panel.add(lightText);
        panel.add(lightButton);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (lightPressed) {
            panel.setPointLight(new Point(e.getX(), e.getY()));
            panel.setLightPlaced();
            panel.repaint();
        } else {
            panel.newControlPoint(e.getX(), e.getY());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cpNumBox) {
            @SuppressWarnings("unchecked")
            JComboBox<String> cpChoice = (JComboBox<String>) e.getSource();
            String choice = (String) cpChoice.getSelectedItem();
            panel.setControlPoints(new ArrayList<Point>());
            panel.setBezPoints(new ArrayList<Point>());
            switch (choice) {
            case "2":
                setLighttoFalse();
                panel.setNthOrder(1);
                break;
            case "3":
                setLighttoFalse();
                panel.setNthOrder(2);
                break;
            case "4":
                setLighttoFalse();
                panel.setNthOrder(3);
                break;
            default:
                setLighttoFalse();
                panel.setNthOrder(2);
                break;
            }
            panel.update(null, null);
        }
    }
    
    
    public void setLighttoFalse() {
        if (lightPressed) {
            lightPressed = false;
            lightButton.setText("Select a light position");
        }
    }
}
