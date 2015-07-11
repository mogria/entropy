import org.playentropy.circuitboard.Vector;

import org.junit.Test;
import org.junit.Assert;

public class VectorTest {

    @Test
    public void testGetX() {
        Assert.assertEquals(-4, new Vector(-4, 3).getX());
    }

    @Test
    public void testGetY() {
        Assert.assertEquals(3, new Vector(-4, 3).getY());
    }

    @Test
    public void testAdd() {
        final Vector x = new Vector(2, -3);
        final Vector r = x.add(new Vector(-1, 2));

        Assert.assertEquals(2, x.getX());
        Assert.assertEquals(-3, x.getY());
        Assert.assertEquals(1, r.getX());
        Assert.assertEquals(-1, r.getY());
    }

    @Test
    public void testArea() {
        Assert.assertEquals(6, new Vector(2, 3).area());
        Assert.assertEquals(6, new Vector(2, -3).area());
        Assert.assertEquals(6, new Vector(-2, 3).area());
        Assert.assertEquals(6, new Vector(-2, -3).area());
        Assert.assertEquals(0, new Vector(-2, 0).area());
        Assert.assertEquals(40, new Vector(2, 20).area());
        Assert.assertEquals(40, new Vector(20, 2).area());
    }

    @Test
    public void testWithinField() {
        final Vector x = new Vector(2, 3);
        Assert.assertTrue(x.withinField(new Vector(0, 0),
                                        new Vector(3, 3)));
        Assert.assertTrue(x.withinField(new Vector(0, 1),
                                        new Vector(3, 3)));
        Assert.assertTrue(x.withinField(new Vector(2, 2),
                                        new Vector(3, 3)));
        Assert.assertTrue(x.withinField(new Vector(2, 3),
                                        new Vector(2, 3)));
        Assert.assertFalse(x.withinField(new Vector(3, 3),
                                         new Vector(2, 2)));
    }

    @Test
    public void testDistance() {
        double d = 0.0;

        d = new Vector(0, -1).distance();
        Assert.assertEquals(1.0, d, Math.ulp(d));

        d = new Vector(1, -1).distance();
        Assert.assertEquals(Math.sqrt(2), d, Math.ulp(d));

        d = new Vector(3, 4).distance();
        Assert.assertEquals(5.0, d, Math.ulp(d));

        d = new Vector(6, 8).distance();
        Assert.assertEquals(10.0, d, Math.ulp(d));
    }
}
