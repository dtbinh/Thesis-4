package jsim;

import org.ejml.data.D1Matrix64F;
import org.ejml.data.DenseMatrix64F;

public class Landmark extends Coordinate {
    private int id;

    public Landmark(double x, double y, int id) {
        super(x, y);
        this.id = id;
    }

    public Landmark(D1Matrix64F x, int id) {
        super(x);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public D1Matrix64F getData() {
        double[] data = {getX(), getY(), id};
        D1Matrix64F result = new DenseMatrix64F();
        result.setData(data);
        return result;
    }
}
