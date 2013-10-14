package jsim;
import static java.lang.Math.*;

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

    public void move() {

    }

    public void move_jacobian() {

    }

    public void observe() {

    }

    public void observe_jacobian() {

    }
}

