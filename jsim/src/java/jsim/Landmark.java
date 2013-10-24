package jsim;

import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

public class Landmark extends Coordinate {
    private Integer id;

    public Landmark(double x, double y, Integer id) {
        super(x, y);
        this.id = id;
    }

    public Landmark(Vector x, Integer id) {
        super(x);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Vector getData() {
        Integer Id = id;
        if (id == null)
            Id = -1;

        double[] data = {getX(), getY(), Id};
        Vector result = new BasicVector(data);
        return result;
    }

}
