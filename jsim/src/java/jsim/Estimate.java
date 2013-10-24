package jsim;

import org.la4j.matrix.Matrix;
import org.la4j.vector.Vector;
import org.la4j.vector.dense.BasicVector;

public class Estimate { // Just a struct
    public Vector state;
    public Matrix covariance;

    public Vector makeState(Pose p, FeatureMap2D m) {
        int N = m.landmarkCount() * 3 + 3;
        java.util.List<Landmark> lmks = m.getLandmarks();
        Vector e = new BasicVector(N);
        e.set(0, p.getX());
        e.set(1, p.getY());
        e.set(2, p.heading());
        int i = 3;
        for (Landmark lmk : lmks) {
            e.set(i, lmk.getX());
            e.set(i + 1, lmk.getY());
            e.set(i + 2, lmk.getId());
        }
        return e;
    }

}
