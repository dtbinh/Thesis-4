package jsim;

import static java.lang.Math.*;
import static org.ejml.alg.dense.mult.MatrixVectorMult.mult;
import org.ejml.data.Matrix64F;
import org.ejml.data.D1Matrix64F;
import org.ejml.data.DenseMatrix64F;
import java.util.Random;
import java.lang.StringBuilder;

public class Coordinate {
    private D1Matrix64F v;
    static private Random generator = new Random();

    protected D1Matrix64F getData() {
        D1Matrix64F data = new DenseMatrix64F();
        data.set(v);
        return data;
    }

    public boolean equals(Object other) {
        Coordinate c = (Coordinate) other;
        double zero = ulp(0.0);
        return abs(getX() - c.getX()) < zero && abs(getY() - c.getY()) < zero;
    }

    public double getX() {
        return v.get(0);
    }

    public double getY() {
        return v.get(1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("X: ");
        sb.append(v.get(0));
        sb.append("\nY: ");
        sb.append(v.get(1));
        return sb.toString();
    }

    static public Coordinate RandomCoordinate(double radius) {
        double r = radius / 2;
        double x = radius * generator.nextDouble() - r;
        double y = radius * generator.nextDouble() - r;
        return new Coordinate(x, y);
    }

    public Coordinate(double x, double y) {
        double[] state = {x,y};
        this.v = new DenseMatrix64F(2, 1, true, state);
    }

    public Coordinate(D1Matrix64F x) {
        this.v = new DenseMatrix64F(x);
    }

    public Coordinate rotate(double angle) {
        double sina = sin(angle);
        double cosa = cos(angle);
        double[] data = {cosa, -sina,
                           sina, cosa};
        DenseMatrix64F R = new DenseMatrix64F(2, 2, true, data);
        D1Matrix64F xr = new DenseMatrix64F(2, 1);
        mult(R,v, xr);
        return new Coordinate(xr); 
    }

    public Coordinate translate(Coordinate frame) {
        double x = getX() + frame.getX();
        double y = getY() + frame.getY();
        return new Coordinate(x, y);
    }

    public Coordinate transform(Coordinate frame, double angle) {
        Coordinate temp = rotate(angle);
        return temp.translate(frame);
    }

    public boolean isVisible(Coordinate position, double angle, double range, double arc) {
        Coordinate landmark = transform(position, angle);
        double x_rt = landmark.getX();
        double y_rt = landmark.getY();

        double d = Math.sqrt(x_rt * x_rt + y_rt * y_rt);
        double b = Math.abs(Math.atan2(y_rt, x_rt)) + Math.PI / 2;
        return d <= range && b <= (arc + angle);
    }

    public double angularDistance(Coordinate other) {
        double xdist = other.getX() - this.getX();
        double ydist = other.getY() - this.getY();
        double angle = atan2(ydist, xdist);
        return angle;
    }

    public double euclidianDistance(Coordinate other) {
        double xdiff = other.getX() - this.getX();
        double ydiff = other.getY() - this.getY();
        double sum = xdiff * xdiff + ydiff * ydiff;
        double result = sqrt(sum);
        return result;
    }
}
