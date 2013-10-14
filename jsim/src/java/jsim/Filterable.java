package jsim;
import java.util.List;
import org.ejml.data.Matrix64F;

public interface Filterable {
   Pose move(Control u, Pose p);
   Matrix64F move_jacobian(Control u, Pose p);
   List<Landmark> observe(List<Landmark> lmks);
   Matrix64F observe_jacobian(List<Landmark> lmks); 
}
