package jsim;

import static java.lang.Math.*;
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

    public Coordinate position() {
        return new Coordinate(x, y);
    }
    
    public double heading() {
        return a;
    }
}
