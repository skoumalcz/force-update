package net.skoumal.forceupdate;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class VersionTest {

    @Test
    public void numberPartsOnlyInit() throws Exception {
        Version v = new Version("0.1.2");

        assertEquals(0, v.getVersionParts()[0]);
        assertEquals(1, v.getVersionParts()[1]);
        assertEquals(2, v.getVersionParts()[2]);
        assertEquals(3, v.getVersionParts().length);
    }

    @Test
    public void doubleDotInit() throws Exception {
        Version v = new Version("0.1..2");

        assertEquals(0, v.getVersionParts()[0]);
        assertEquals(1, v.getVersionParts()[1]);
        assertEquals(2, v.getVersionParts().length);
    }

    @Test
    public void numberPartsAndStuffInit() throws Exception {
        Version v = new Version("6.33.5.4-beta");

        assertEquals(6, v.getVersionParts()[0]);
        assertEquals(33, v.getVersionParts()[1]);
        assertEquals(5, v.getVersionParts()[2]);
        assertEquals(4, v.getVersionParts()[3]);
    }

    @Test
    public void invalidStringInit() throws Exception {

        try {
            new Version("");
            fail("Should throw exception.");
        } catch (RuntimeException e) {
            // desired behaviour
        }

        try {
            new Version(".4.5");
            fail("Should throw exception.");
        } catch (RuntimeException e) {
            // desired behaviour
        }

        try {
            new Version("-4.5");
            fail("Should throw exception.");
        } catch (RuntimeException e) {
            // desired behaviour
        }

        try {
            new Version("a.b.c");
            fail("Should throw exception.");
        } catch (RuntimeException e) {
            // desired behaviour
        }
    }




    @Test
    public void versionPartsInit() throws Exception {
        Version v = new Version(new int [] {3, 4, 1});

        assertEquals(3, v.getVersionParts()[0]);
        assertEquals(4, v.getVersionParts()[1]);
        assertEquals(1, v.getVersionParts()[2]);
    }

    @Test
    public void equality() throws Exception {
        Version v1 = new Version(new int [] {3, 4, 1});
        Version v2 = new Version("3.4.1");

        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
        assertTrue(v1.hashCode() == v2.hashCode());
    }

    @Test
    public void inequality() throws Exception {
        Version v1 = new Version(new int [] {3, 2, 1});
        Version v2 = new Version("3.4.1");

        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));
        assertFalse(v1.hashCode() == v2.hashCode());

        v1 = new Version(new int [] {2, 4, 1});

        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));
        assertFalse(v1.hashCode() == v2.hashCode());

        v1 = new Version(new int [] {3, 4, 99});

        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));
        assertFalse(v1.hashCode() == v2.hashCode());
    }

    @Test
    public void comparisonEqualLength() throws Exception {
        Version v1 = new Version("3.4.1");
        Version v2 = new Version("3.4.1");

        assertTrue(v1.compareTo(v2) == 0);
        assertTrue(v2.compareTo(v1) == 0);

        v1 = new Version("3.4.2");

        assertTrue(v1.compareTo(v2) > 0);
        assertTrue(v2.compareTo(v1) < 0);

        v1 = new Version("4.4.1");

        assertTrue(v1.compareTo(v2) > 0);
        assertTrue(v2.compareTo(v1) < 0);
    }

    @Test
    public void comparisonDifferentLengths() throws Exception {
        Version v1 = new Version("3.4.1.1");
        Version v2 = new Version("3.4.1");

        assertTrue(v1.compareTo(v2) == 0);
        assertTrue(v2.compareTo(v1) == 0);

        v1 = new Version("3.4");

        assertTrue(v1.compareTo(v2) == 0);
        assertTrue(v2.compareTo(v1) == 0);

        v1 = new Version("3.4.2.1");

        assertTrue(v1.compareTo(v2) > 0);
        assertTrue(v2.compareTo(v1) < 0);

        v1 = new Version("3.5");

        assertTrue(v1.compareTo(v2) > 0);
        assertTrue(v2.compareTo(v1) < 0);
    }

}
