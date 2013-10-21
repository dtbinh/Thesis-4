package jsim;
import static java.lang.Math.*;
import org.ejml.data.DenseMatrix64F;

/** 
 * @author John Downs <john.downs @ ieee.org>
 */
public class Robot {
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

    /*
    * Note, addition of old pose is done in this function!
    */
    public Pose move(Control u, Pose x) {
        double v = u.linearVelocity();
        double w = u.angularVelocity();
        double a = x.heading();
        Coordinate c;
        Pose result;

        if (w == 0) {
           c = new Coordinate(x.getX() + v * cos(a), x.getY() + v * sin(a));
           result = new Pose(c, a);
           return result; 
        }
        c = new Coordinate(x.getX() + -v/w * sin(a) + v/w * sin(a + w), 
                           x.getY() +  v/w * cos(a) - v/w * cos(a + w));
        Pose p = new Pose(c, a + w);
        return p;
    }

    public DenseMatrix64F move_jacobian(Control u, Pose x) {
        double[][] data;
        double v = u.linearVelocity();
        double w = u.angularVelocity();
        double a = x.heading();
        if (w == 0.0) {
            data = new double[][] {{0, 0, -v * sin(a)},
                    {0, 0,  v * sin(a)  },
                    {0, 0, 0}};
        } else {
            data = new double[][] {{0, 0, -v/w * cos(a) + v/w * cos(a + w)},
                    {0, 0,  v/w * sin(a) + v/w * sin(a + w)},
                    {0, 0, 0}};
        }
        DenseMatrix64F jacobian = new DenseMatrix64F(data); //6 x 3
        return jacobian;
    }

    private double squareDotProduct(double[] v) {
        return v[0] * v[0] + v[1] * v[1];
    }

    private double wrap(double a) {
        return a;
    }

    public Landmark observe(Pose p, Landmark c) {
        double dx = c.getX() - p.getX();
        double dy = c.getY() - p.getY();
        double a = p.heading();
        double[] d = {dx, dy};
        double q = squareDotProduct(d);
        Landmark L =  new Landmark(sqrt(q), wrap(atan2(dy, dx) - a), c.getId());
        return L;
    }

    public DenseMatrix64F observe_jacobian(Pose p, Landmark c) {
        double[][] data;
        //TODO: Make sure Landmarks have ID
        double dx = c.getX() - p.getX();
        double dy = c.getY() - p.getY();
        double[] d = {dx, dy};
        double q = squareDotProduct(d);
        double k;
        if (q == 0) {
            k = 0;
        } else {
            k = 1/q;
        }
        double sq = sqrt(q);
        double ksqx = k * sq * dx;
        double ksqy = k * sq * dy;
        double kdy = k * dy;
        double kdx = k * dx;
        data = new double[][] {{-ksqx, -ksqy, ksqx, ksqy},
                {kdy, kdx, k*q, -kdy, kdx, 0},
                {0, 0, 0 ,0 ,0, k*q}};
        DenseMatrix64F jacobian = new DenseMatrix64F(data);
        return jacobian;
    }
}

