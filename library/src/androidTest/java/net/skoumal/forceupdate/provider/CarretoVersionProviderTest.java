package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionResult;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by gingo on 20.11.2016.
 */
public class CarretoVersionProviderTest {

    @Test
    public void loadVersion() throws Exception {
        CarretoVersionProvider provider = new CarretoVersionProvider("com.vocabularyminer.android");

        VersionResult result = provider.getVersion();

        // TODO [1] avoid failing when offline

        assertFalse(result.isError());
        assertNotNull(result);
        assertNotNull(result.getVersion());
        assertNull(result.getErrorMessage());
        assertNull(result.getErrorException());

        assertEquals(result.getVersion().toString(), new Version("1.0.1").toString());

    }

    // TODO [1] test offline response
}
