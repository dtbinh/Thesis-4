package jsim;

/**
 * Control signal for 2D motion
 */
public class Control {
    private double linear;
    private double angular;
    public Control(double linear_velocity, double angular_velocity) {
        linear = linear_velocity;
        angular = angular_velocity;
    }    
    /*
     *  Linear velocity - meters per second
     */
    public double linearVelocity() {
        return linear;
    }

    /*
     *  Angular velocity - radians per second
     */
    public double angularVelocity() {
        return angular;
    }
}
