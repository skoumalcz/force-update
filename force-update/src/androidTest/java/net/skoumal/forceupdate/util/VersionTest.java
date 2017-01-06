package net.skoumal.forceupdate.util;

import net.skoumal.forceupdate.Version;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gingo on 4.12.2016.
 */
public class VersionTest {
    @Test
    public void baseTest()
    {
        List<Version> versions = new ArrayList<>();
        Version v1 = new Version("1.0.0");
        Version v2 = new Version("1.1.0");
        Version v3 = new Version("22.0");
        versions.add(v1);
        versions.add(v2);
        versions.add(v3);
        Set<String> set = Versions.toStringSet(versions);
        assertTrue(set.contains("1.0.0"));
        assertTrue(set.contains("1.1.0"));
        assertTrue(set.contains("22.0"));
        assertFalse(set.contains("1.5.1"));
    }

    @Test
    public void sizeTest()
    {
        List<Version> versions = new ArrayList<>();
        for(int i = 0; i< 6; i++)
        {
            Version v = new Version("1." +i + ".0");
            versions.add(v);
        }
        Set<String> set = Versions.toStringSet(versions);
        assertSame(set.size(), versions.size());
    }
    @Test
    public void nullTest()
    {
        List<Version> versions = new ArrayList<>();
        versions.add(null);
        Set<String> set = Versions.toStringSet(versions);
        fail("Should throw NullPointerException");
    }

}
