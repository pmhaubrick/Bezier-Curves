package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.sun.accessibility.internal.resources.accessibility;


public class LightingLogic {

    ArrayList<Double> unitNormalsX = new ArrayList<Double>();
    ArrayList<Double> unitNormalsY = new ArrayList<Double>();
    ArrayList<Point> bezPoints;


    public ArrayList<Point> calculateNormals(ArrayList<Point> bez) {
        ArrayList<Point> normals = new ArrayList<Point>();
        bezPoints = bez;
        unitNormalsX = new ArrayList<Double>();
        unitNormalsY = new ArrayList<Double>();
        for (int i = 0; i < bezPoints.size(); i++) {
            if (i != 0) {
                Point b0 = bezPoints.get(i - 1);
                Point b1 = bezPoints.get(i);
                Point midPoint = new Point((int) (Math.ceil(b0.getX() + ((b1.getX() - b0.getX()) * 0.5))), (int) (Math.ceil(b0.getY() + ((b1.getY() - b0.getY()) * 0.5))));
                normals.add(midPoint);
                double relativeNormalX = (b1.getY() - midPoint.getY()) * -1;
                double relativeNormalY = b1.getX() - midPoint.getX();
                double magnitude = Math.sqrt((relativeNormalX * relativeNormalX) + (relativeNormalY * relativeNormalY));
                if (magnitude == 0) {
                    magnitude = 1;
                }
                unitNormalsX.add(midPoint.getX() - relativeNormalX / magnitude * 100);
                unitNormalsY.add(midPoint.getY() - relativeNormalY / magnitude * 100);
                Point unitNormalScaled = new Point((int) (Math.ceil(midPoint.getX() - relativeNormalX / magnitude * 10)), (int) (Math.ceil(midPoint.getY() - relativeNormalY / magnitude * 10)));
                normals.add(unitNormalScaled);
            }
        }
        return normals;
    }


    public ArrayList<Double> calculateLightIntensity(Point lightSource, ArrayList<Point> normals) {
        ArrayList<Double> illuminance = new ArrayList<Double>();
        for (int i = 0; i < normals.size() / 2; i++) {
            double lightDotProduct;
            if (hasIntersept(lightSource, normals.get(i * 2), i)) {
                lightDotProduct = 0.0;
            } else {
                double relativeNormalX = lightSource.getX() - normals.get(i * 2).getX();
                double relativeNormalY = lightSource.getY() - normals.get(i * 2).getY();
                double magnitude = Math.sqrt((relativeNormalX * relativeNormalX) + (relativeNormalY * relativeNormalY));
                double unitLightVectorX = (normals.get(i * 2).getX() + relativeNormalX / magnitude);
                double unitLightVectorY = (normals.get(i * 2).getY() + relativeNormalY / magnitude);
                lightDotProduct = ((unitLightVectorX - normals.get(i * 2).getX()) * (unitNormalsX.get(i) - normals.get(i * 2).getX()) + ((unitLightVectorY - normals.get(i * 2).getY()) * (unitNormalsY.get(i) - normals.get(i * 2).getY()))) / 100;
                if (lightDotProduct < 0) {
                    lightDotProduct = 0.0;
                } else if (lightDotProduct > 1) {
                    lightDotProduct = 1.0;
                }
            }
            illuminance.add(lightDotProduct);
        }
        return illuminance;
    }


    public boolean hasIntersept(Point lightPos, Point samplePos, int currentSample) {
        for (int i = 0; i < bezPoints.size() - 1; i++) {
            if (i != (double) currentSample) {
                int orientation1 = orientationClocwise(samplePos, lightPos, bezPoints.get(i));
                int orientation2 = orientationClocwise(samplePos, lightPos, bezPoints.get(i + 1));
                int orientation3 = orientationClocwise(bezPoints.get(i), bezPoints.get(i + 1), samplePos);
                int orientation4 = orientationClocwise(bezPoints.get(i), bezPoints.get(i + 1), lightPos);
                if (orientation1 != orientation2 && orientation3 != orientation4) {
                    return true;
                }
            }
        }
        return false;
    }


    public int orientationClocwise(Point p1, Point p2, Point p3) {
        int val = (p2.y - p1.y) * (p3.x - p2.x) - (p2.x - p1.x) * (p3.y - p2.y);
        if (val == 0) {
            return 0;
        } else {
            return (val > 0) ? 1 : 2;
        }
    }
}
