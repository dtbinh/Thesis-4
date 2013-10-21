package jsim;

import org.ejml.data.D1Matrix64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class EKF {
    private static Pose getPose(Estimate prior) {
        Pose pose = new Pose(prior.state().get(0,0),
                             prior.state().get(0,1),
                             prior.state().get(0,2));
        return pose;
    }

    private static DenseMatrix64F square(DenseMatrix64F m1, DenseMatrix64F m2) {
        DenseMatrix64F tmp = m1.transpose().mult(m2);
        DenseMatrix64F result = tmp.mult(m1);
    }
    private static Estimate predict(Robot r, Map m0, Control u) {
        Estimate prediction = new Estimate();
        Pose x0_r = r.getPose();
        Pose x1_est = r.move(u, x0_r);
        prediction.state = x1_est;
        prediction.covariance = p1_est;
        return prediction;
    }
    
    public static Estimate run(Robot r, Map2D prior, Control u,
                                  java.util.List<Landmark> z) {
        Pose x0_r = r.getPose();
        Map2D x0_m = prior.getLandmarks();
        Pose x1 = r.move(u, x0);
        int N = prior.landmarkCount();
        DenseMatrix64F x1_prime = r.move_jacobian(u, x0);
      	DenseMatrix64F I3 = CommonOps.identity(3);
        DenseMatrix64F Z3N = new DenseMatrix64F(3, 3*N);
        DenseMatrix64F F = [I3, Z3N];
        DenseMatrix64F G = Ig + F.transpose() * x1_prime * F;
        DenseMatrix64F R = I3 * r.motionNoise();
        DenseMatrix64F Q = I3 * r.sensorNoise();
        return null;
    }
}
