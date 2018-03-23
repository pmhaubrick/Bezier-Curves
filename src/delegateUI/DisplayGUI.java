package delegateUI;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import model.Model;


public class DisplayGUI {

    public static final int FRAME_WIDTH = 1200; // Width constant for GUI .
    public static final int FRAME_HEIGHT = 800; // Height constant for GUI.
    protected Panel canvas;
    private static JFrame frame;
    private static Model model;


    public DisplayGUI(Model m) {
        model = m;
        initialiseGUI(); // Initialises the basic GUI settings.
    }


    private void initialiseGUI() {
        frame = new JFrame();
        frame.setTitle("Computer Graphics P1 - Bézier Curves");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits program correctly when 'x' is pressed.
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); // Sets the window dimensions.
        frame.setLocationRelativeTo(null); // Spawns GUI window in the centre of the screen.
        frame.setResizable(false); // Forces the window to be a fixed size.
        addPanel(); // Method for adding a Panel to the Frame just initialised.
        frame.setVisible(true); // Makes everything visible.
    }


    public void addPanel() {
        canvas = new Panel(model);
        canvas.setSize(FRAME_WIDTH, FRAME_HEIGHT); // Sizes for canvas.
        canvas.setVisible(true); // Sets the new canvas to being visible.
        frame.add(canvas, BorderLayout.CENTER); // Adds canvas to the centre of the JFrame.
    }


    public static void updateModel(int nthOrder, ArrayList<Point> controlPoints, double t) {
        model.updateBezierCurve(nthOrder, controlPoints, t);
    }
    
    
    public static void updateIlluminance(Point lightSource) {
        model.updateLightModel(lightSource);
    }

}
