package delegateUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.sun.scenario.effect.light.PointLight;

import model.Model;


@SuppressWarnings("serial")
public class Panel extends JPanel implements Observer {

    private ArrayList<Point> controlPoints = new ArrayList<Point>();
    private ArrayList<Point> bezPoints = new ArrayList<Point>();
    private ArrayList<Point> normals = new ArrayList<Point>();
    private ArrayList<Double> illuminance = new ArrayList<Double>();
    private int nthOrder = 2;
    private double tIncrement = 0.05;
    private int lightPlaced = 0;
    private Model model;
    private Point pointLight;


    public Panel(Model m) {
        model = m;
        model.addObserver(this);
        this.addMouseListener(new UserInputListeners(this));
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(158, 215, 239));
        drawControlPoints(g);
        drawBezCurve(g);
        if (lightPlaced > 0) {
            drawLight(g);
            DisplayGUI.updateIlluminance(pointLight);
            drawIlluminance(g);
        }
    }


    public void newControlPoint(int x, int y) {
        Point newCP = new Point(x, y);
        boolean repeatedPoint = false;
        for (Point cp : controlPoints) {
            if ((newCP.toString().equals(cp.toString()))) {
                repeatedPoint = true;
            }
        }
        if (!repeatedPoint) {
            if (controlPoints.size() >= nthOrder + 1) {
                controlPoints.remove(0);
            }
            controlPoints.add(newCP);
        }
        if (controlPoints.size() >= nthOrder + 1) {
            DisplayGUI.updateModel(nthOrder, controlPoints, tIncrement);
        } else {
            repaint();
        }
    }


    public void drawControlPoints(Graphics g) {
        int i = 1;
        for (Point p : controlPoints) {
            g.setColor(Color.BLUE);
            g.fillOval(p.x - 8, p.y - 10, 14, 14);
            g.drawString("P" + i, p.x - 8, p.y - 20);
            i++;
        }
    }


    private void drawBezCurve(Graphics g) {
        bezPoints = model.getBezPoints();
        normals = model.getnormals();
        if (controlPoints.size() == nthOrder + 1) {
            for (int i = 0; i < bezPoints.size(); i++) {
                g.setColor(Color.RED);
                g.fillOval(bezPoints.get(i).x - 3, bezPoints.get(i).y - 3, 6, 6);
                if (i != 0) {
                    g.drawLine(bezPoints.get(i - 1).x, bezPoints.get(i - 1).y, bezPoints.get(i).x, bezPoints.get(i).y);
                }
            }
        }
    }


    public void drawIlluminance(Graphics g) {
        illuminance = model.getIlluminance();
        for (int i = 0; i < illuminance.size(); i++) {
            g.setColor(new Color((int) (255 * illuminance.get(i)), (int) (255 * illuminance.get(i)), (int) (255 * illuminance.get(i))));
            g.fillOval(normals.get(i * 2).x - 8, normals.get(i * 2).y - 8, 15, 15);
        }
    }


    public void drawLight(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(pointLight.x - 20, pointLight.y - 20, 40, 40);
    }


    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }


    public void setControlPoints(ArrayList<Point> cp) {
        controlPoints = cp;
    }


    public void setBezPoints(ArrayList<Point> bp) {
        bezPoints = bp;
        model.setBezPoints(bp);
    }


    public void setNthOrder(int n) {
        nthOrder = n;
        lightPlaced = 0;
    }


    public void setTIncrement(double tInterval) {
        tIncrement = tInterval;
    }


    public void setLightPlaced() {
        lightPlaced++;
    }


    public void setPointLight(Point p) {
        pointLight = p;
    }

}
