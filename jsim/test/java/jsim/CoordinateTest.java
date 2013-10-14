package jsim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.lang.Math;

/**
 * Tests for {@link jsim.Coordinates}.
 *
 * @author john.downs@ieee.org (John Downs)
 */
@RunWith(JUnit4.class)
public class CoordinateTest {
    double delta = 1E-7;

    @Test
    public void test_compare() {
        Coordinate foo = new Coordinate(0,0);
        Coordinate bar = new Coordinate(0.0, 0.0);
        assertEquals(true, foo.equals(bar));
    }
    @Test
    public void test_getters() {
        Coordinate foo = new Coordinate(1.0, 2.0);
        assertEquals(1.0, foo.getX(), delta);
        assertEquals(2.0, foo.getY(), delta);
    }
    @Test
    public void test_rotation() {
        Coordinate foo = new Coordinate(1.0, 0.0);
        Coordinate rot = foo.rotate(Math.PI/2);
        assertEquals(0.0, rot.getX(), delta);
        assertEquals(1.0, rot.getY(), delta);
    }
    @Test
    public void test_translation() {
        Coordinate foo = new Coordinate(1.0, 0.0);
        Coordinate bar = new Coordinate(2.0, 1.0);
        Coordinate trn = foo.translate(bar);
        assertEquals(3.0, trn.getX(), delta);
        assertEquals(1.0, trn.getY(), delta);
    }
    @Test
    public void test_transformation() {
        Coordinate foo = new Coordinate(1.0, 0.0);
        Coordinate bar = new Coordinate(2.0, 1.0);
        Coordinate trf = foo.transform(bar, Math.PI/2);
        assertEquals(2.0, trf.getX(), delta);
        assertEquals(2.0, trf.getY(), delta);
    }
}
