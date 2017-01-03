package net.skoumal.forceupdate.provider;

import android.app.Application;
import android.support.test.InstrumentationRegistry;

import net.skoumal.forceupdate.OfflineHelper;
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
public class ApkVersionProviderTest {

    @Test
    public void loadVersion() throws Exception {
        ApkVersionProvider provider = new ApkVersionProvider((Application)InstrumentationRegistry.getTargetContext().getApplicationContext());

        VersionResult result = provider.getVersionResult();

        if(!OfflineHelper.ignoreOfflineErrors()) {
            assertFalse(result.isError());
            assertNotNull(result);
            assertNotNull(result.getVersion());
            assertNull(result.getErrorMessage());
            assertNull(result.getErrorException());

            assertEquals(result.getVersion().toString(), new Version("0.1.0").toString());
        }

    }
}
