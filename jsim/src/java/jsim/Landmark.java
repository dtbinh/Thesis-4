package jsim;
import org.ejml.data.D1Matrix64F;

public class Landmark extends Coordinate {
    private int id;


    public Landmark(double x, double y, int id) {
        super(x, y);
        this.arc = arc;
    }
    public Landmark(D1Matrix64F x, int id) {

    }

    public double getRange() {
        return range;
    }
    public double getArc() {
        return arc;
    }
}
