package jsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.lang.Math;

/**
 * Tests for {@link jsim.Control}.
 *
 * @author jdowns@ieee.org (John Downs)
 */
@RunWith(JUnit4.class)
public class ControlTest {
    double delta = 1E-7;

    @Test
    public void test_velocity() {
        Control u = new Control(1,2);
        assertEquals(1, u.linearVelocity());
        assertEquals(2, u.angularVelocity());
    }
}
