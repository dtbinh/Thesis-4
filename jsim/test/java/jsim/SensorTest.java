package jsim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.lang.Math;

/**
 * Tests for {@link jsim.Sensor}.
 *
 * @author jdowns@ieee.org (John Downs)
 */
@RunWith(JUnit4.class)
public class SensorTest {
    double delta = 1E-7;

    @Test
    public void test_sensor_parameters() {
        Sensor s = new Sensor(10, 3.14);
        assertEquals(10, s.getRange());
        assertEquals(3.14, s.getArc());
    }
}
