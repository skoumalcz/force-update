package net.skoumal.forceupdate.util;

import net.skoumal.forceupdate.Version;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        Version v5 = new Version("22.0");
        versions.add(v1);
        versions.add(v2);
        versions.add(v5);
        Set<String> set = Versions.toStringSet(versions);
        assertTrue(set.contains("1.0.0"));
        assertTrue(set.contains("1.1.0"));
        assertTrue(set.contains("22.0"));
        assertFalse(set.contains("1.5.1"));
    }

}
