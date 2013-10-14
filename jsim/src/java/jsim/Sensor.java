package jsim;

public class Sensor {
    private double range;
    private double arc;

    public Sensor(double range, double arc) {
        this.range = range;
        this.arc = arc;
    }

    public double getRange() {
        return range;
    }
    public double getArc() {
        return arc;
    }
}
