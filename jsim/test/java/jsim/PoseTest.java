package jsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.lang.Math;

/**
 * Tests for {@link jsim.Pose}.
 *
 * @author jdowns@ieee.org (John Downs)
 */
@RunWith(JUnit4.class)
public class PoseTest {
    double delta = 1E-7;

    @Test
    public void test_position() {
        Coordinate foo = new Coordinate(0,1);
        Pose p = new Pose(0,1,2);
        assertTrue(foo.equals(p.position()));
    }

    @Test
    public void test_heading() {
        Pose p = new Pose(0,1,2.0);
        assertEquals(2.0, p.heading(), delta);
    }
}
