package model;

import java.awt.Point;
import java.util.ArrayList;


public class BezierLogic {

    public ArrayList<Point> linearBez(ArrayList<Point> cp, double tIncrement) {
        ArrayList<Point> bezPoints = new ArrayList<Point>();
        for (double t = 0; t <= 1; t += tIncrement) {
            int xt = (int) (((1 - t) * cp.get(0).getX()) + (t * cp.get(1).getX()));
            int yt = (int) (((1 - t) * cp.get(0).getY()) + (t * cp.get(1).getY()));
            bezPoints.add(new Point(xt, yt));
        }
        return bezPoints;
    }


    public ArrayList<Point> quadraticBez(ArrayList<Point> cp, double tIncrement) {
        ArrayList<Point> bezPoints = new ArrayList<Point>();
        for (double t = 0; t <= 1; t += tIncrement) {
            int xt = (int) (((1 - t) * (1 - t) * cp.get(0).getX()) + (2 * t * (1 - t) * cp.get(1).getX()) + (t * t * cp.get(2).getX()));
            int yt = (int) (((1 - t) * (1 - t) * cp.get(0).getY()) + (2 * t * (1 - t) * cp.get(1).getY()) + (t * t * cp.get(2).getY()));
            bezPoints.add(new Point(xt, yt));
        }
        return bezPoints;
    }


    public ArrayList<Point> cubicBez(ArrayList<Point> cp, double tIncrement) {
        ArrayList<Point> bezPoints = new ArrayList<Point>();
        for (double t = 0; t <= 1; t += tIncrement) {
            int xt = (int) (((1 - t) * (1 - t) * (1 - t) * cp.get(0).getX()) + (3 * t * (1 - t) * (1 - t) * cp.get(1).getX()) + (3 * (1 - t) * t * t * cp.get(2).getX()) + (t * t * t * cp.get(3).getX()));
            int yt = (int) (((1 - t) * (1 - t) * (1 - t) * cp.get(0).getY()) + (3 * t * (1 - t) * (1 - t) * cp.get(1).getY()) + (3 * (1 - t) * t * t * cp.get(2).getY()) + (t * t * t * cp.get(3).getY()));
            bezPoints.add(new Point(xt, yt));
        }
        return bezPoints;
    }
}
