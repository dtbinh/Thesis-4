package jsim;

import org.la4j.matrix.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.factory.CCSFactory;
import org.la4j.factory.Basic2DFactory;

public class EKF {
    /*private static Pose getPose(Estimate prior) {
        Pose pose = new Pose(prior.state.get(0),
                             prior.state.get(1),
                             prior.state.get(2));
        return pose;
    }

    public static Estimate predict(Robot r, Estimate e, Vector u) {
        Vector x0 = estimate.getState();
        Matrix P0 = estimate.getCovariance();

        Vector x = r.predictState(x0, u); //predicted state
        Matrix F = r.stateTransitionMatrix(x0, u); //state transition matrix
        Matrix F1 = F.transpose();
        Matrix Q = r.processNoiseCovariance(3);
        Vector P = F.multiply(P0).multiply(F1).add(Q);
        return new Estimate(x, Q);
    }

    private static Estimate update_one(Robot r, Estimate e, Vector z, int id) {
        Vector x_pred = e.getState();
        Matrix P_pred = e.getCovariance();
        Vector y = z.subtract(r.predictObservation(x_pred, id)); //innovation
        Matrix H = r.observationMatrix(x0, id);
        Matrix H1 = H.transpose();
        Matrix R = r.observationNoiseCovariance(size);
        Matrix S = H.multiply(P).multiply(H1).add(R); // innovation covariance
        Matrix Si = S.withSmartInverter().inverse();
        Matrix K = P.multiply(H1).multiply(Si); // Kalman Gain
        Vector x = x_pred.add(K.multiply(y));
        Matrix P = (I.minus(K.multiply(G))).multiply(P);
        return new Estimate(x, P);
    }
    public static Estimate update(Robot r, Estimate e, List<Vector> observations) {
        
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
        }*/
}
