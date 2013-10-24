package jsim;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.factory.CCSFactory;
import org.la4j.factory.Basic2DFactory;

public class EKF {
    private static Pose getPose(Estimate prior) {
        Pose pose = new Pose(prior.state.get(0),
                             prior.state.get(1),
                             prior.state.get(2));
        return pose;
    }

    public static Matrix square(Matrix m1, Matrix m2) {
        Matrix tmp = m1.transpose().multiply(m2);
        Matrix result = tmp.multiply(m1);
        return result;
    }

    private static Estimate predict(Robot r, FeatureMap2D m0, Control u) {
        CCSFactory I = new CCSFactory();
        Estimate prediction = new Estimate();
        double motion_noise = r.motionNoise();
        Pose x0_r = r.getPose();
        Pose x1_est = r.move(u, x0_r);

        Matrix p1_0 = p.getCovariance();
        Matrix p1_est = new Basic2DMatrix(3,3);
        Matrix I3 = I.createIdentityMatrix(3);

        prediction.state = x1_est.asVector();
        prediction.covariance = p1_est;
        return prediction;
    }

    public static Estimate run(Robot r, FeatureMap2D prior, Control u,
                                  java.util.List<Landmark> z) {
        CCSFactory I = new CCSFactory();
        Basic2DFactory b = new Basic2DFactory();
        Pose x0_r = r.getPose();
        Pose x1 = r.move(u, x0_r);
        int N = prior.landmarkCount();
        Matrix x1_prime = r.move_jacobian(u, x0_r);
      	Matrix I3 = I.createIdentityMatrix(3);
        Matrix Z3N = new Basic2DMatrix(3, 3*N);
        Matrix F = b.createBlockMatrix(I3, Z3N, null, null);
        Matrix G = I3.add(square(F, x1_prime));
        Matrix R = I3.multiply(r.motionNoise());
        Matrix Q = I3.multiply(r.sensorNoise());
        return null;
    }
}
