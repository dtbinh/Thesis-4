package jsim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

/**
 * Tests for {@link jsim.Robot}.
 *
 * @author jdowns@ieee.org (John Downs)
 */
@RunWith(JUnit4.class)
public class Map2DTest {
    double delta = 1E-7;

    @Test
    public void test_getvisiblelandmarks() {
       List<Coordinate> c = new ArrayList<Coordinate>();
       c.add(new Coordinate(0.0, 0.0));
       c.add(new Coordinate(1.0, 0.0));
       c.add(new Coordinate(-1.0, 0.0));
       Map2D m = new Map2D(c, null); 
       Sensor s = new Sensor(2, Math.PI/2);
       List<Coordinate> landmarks = m.getVisibleLandmarks(new Robot(s, 1));
       assertEquals(2, landmarks.size());
    }

    @Test 
    public void test_getwaypoint() {
       List<Coordinate> c = new ArrayList<Coordinate>();
       c.add(new Coordinate(0.0, 0.0));
       c.add(new Coordinate(1.0, 0.0));
       c.add(new Coordinate(-1.0, 0.0));
       Map2D m = new Map2D(null, c);

    }
}

