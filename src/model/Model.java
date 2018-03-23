package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;


public class Model extends Observable {

    private ArrayList<Point> bezPoints = new ArrayList<Point>();
    private ArrayList<Point> normals = new ArrayList<Point>();
    private ArrayList<Double> illuminance = new ArrayList<Double>();
    private BezierLogic bez;
    private LightingLogic light;


    public Model() {
        bez = new BezierLogic();
        light = new LightingLogic();
    }


    public void updateBezierCurve(int nthOrder, ArrayList<Point> controlPoints, double t) {
        if (nthOrder == 1) {
            bezPoints = bez.linearBez(controlPoints, t);
        } else if (nthOrder == 2) {
            bezPoints = bez.quadraticBez(controlPoints, t);
        } else if (nthOrder == 3) {
            bezPoints = bez.cubicBez(controlPoints, t);
        }
        bezPoints.add(controlPoints.get(controlPoints.size() - 1));
        normals = light.calculateNormals(bezPoints);
        setChanged();
        notifyObservers();
    }


    public void updateLightModel(Point lightSource) {
        illuminance = light.calculateLightIntensity(lightSource, normals);
        setChanged();
        notifyObservers();
    }


    public ArrayList<Point> getBezPoints() {
        return bezPoints;
    }


    public ArrayList<Point> getnormals() {
        return normals;
    }


    public void setBezPoints(ArrayList<Point> bp) {
        bezPoints = bp;
    }


    public void setnormals(ArrayList<Point> n) {
        normals = n;
    }


    public ArrayList<Double> getIlluminance() {
        return illuminance;
    }
}
