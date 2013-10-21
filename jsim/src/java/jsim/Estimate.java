package jsim;

import org.ejml.data.D1Matrix64F;
import org.ejml.data.DenseMatrix64F;

public class Estimate { // Just a struct
    public D1Matrix64F state;
    public DenseMatrix64F covariance;
}
