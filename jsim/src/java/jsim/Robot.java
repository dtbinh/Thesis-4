package jsim;
import static java.lang.Math.*;
import org.ejml.data.DenseMatrix64F;

/** 
 * @author John Downs <john.downs @ ieee.org>
 */
public class Robot implements Filterable {
    private double maxVelocity;
    private Sensor sensor;
    private Pose pose;

    /**
     *  Create a robot with a sensor and maximum velocity
     *  @param sensor Range/bearing sensor @see jsim.sensor
     *  @param maxVelocity Max velocity for both angular and linear motion
     */ 
    public Robot(Sensor sensor, double maxVelocity) {
        pose = new Pose(0,0,0);
        this.sensor = sensor;
        this.maxVelocity = maxVelocity;
    }

    /**
     * @return Current estimated pose
     */
    public Pose getPose() {
        return pose;
    }

    /**
     * @return Current estimated heading
     */
    public double getHeading() {
        return pose.heading();
    }

    /**
     * @return Current estimated position
     */
    public Coordinate getPosition() {
        return pose.position();
    }

    /**
     * @return Sensor arc in radians
     */
    public double sensorArc() {
        return sensor.getArc();
    }

    /** 
     * @return Sensor range in meters
     */
    public double sensorRange() {
        return sensor.getRange(); 
    }

    public Control plan(Pose p, Coordinate waypoint) {
        Coordinate robot_position = p.position();
        double angular_distance = robot_position.angularDistance(waypoint) - p.heading();
        double linear_distance = robot_position.euclidianDistance(waypoint);
        double w = min(angular_distance, maxVelocity);
        double v = min(linear_distance, maxVelocity); 
        return new Control(v, w);
    }

    public Pose move(Control u, Pose x) {
        double v = u.linearVelocity();
        double w = u.angularVelocity();
        double a = x.getHeading();
        Coordinate c;
        Pose result;

        if (w == 0) {
           c = new Coordinate(x.getX() + v * cos(a), x.getY() + v * sin(a));
           result = new Pose(c, a);
           return result; 
        }
        c = new Coordinate(x.getX() + -v/w * sin(a) + v/w * sin(a + w), 
                           x.getY() +  v/w * cos(a) - v/w * cos(a + w));
        p = new Pose(c, a + w);
        return p;
    }

    public DenseMatrix64F move_jacobian() {

    }

    public Landmark observe(Pose p, Landmark c) {
        double dx = c.getX() - p.getX();
        double dy = c.getY() - p.getY();
        double a = p.getHeading();
        DenseMatrix64F d = new DenseMatrix64F(2,1, dx, dy);
        double q = dot(d,d);
        Landmark L =  new Landmark(sqrt(q), wrap(atan2(dy, dx) - a), c.getId);
        return L;
    }

    public DenseMatrix64F observe_jacobian() {
        
    }
}

