package jsim;

public class Sensor {
    private double range;
    private double arc;
    private double sensorNoise;

    public Sensor(double range, double arc, double noise) {
        this.range = range;
        this.arc = arc;
        this.sensorNoise = noise;
    }

    public double getRange() {
        return range;
    }
    public double getArc() {
        return arc;
    }

    public double noise() {
        return sensorNoise;
    }
}
