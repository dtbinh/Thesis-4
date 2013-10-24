package jsim;

import static java.lang.Math.*;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

/**
 ** @author      John Downs <john.downs@ieee.org>
 ** @version     0.1
 **/
public class Pose {
    double x;
    double y;
    double a;

    /**
     * Creates 2D pose with position and orientation
     * @param x Horizontal position
     * @param y Vertical position
     * @param heading Radians between 0 and 2*pi
     */
    public Pose(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.a = atan2(sin(heading), cos(heading));
    }

    public Pose(Coordinate c, double heading) {
        this.x = c.getX();
        this.y = c.getY();
        this.a = heading;
    }

    public Coordinate position() {
        return new Coordinate(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double heading() {
        return a;
    }

    public Vector asVector() {
        Vector result = new BasicVector(new double[] {x, y, a});
        return result;
    }
}
