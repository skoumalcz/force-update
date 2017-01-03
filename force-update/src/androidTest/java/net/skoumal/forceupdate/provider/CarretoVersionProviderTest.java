package net.skoumal.forceupdate.provider;

import android.support.test.filters.SdkSuppress;

import com.linkedin.android.testbutler.TestButler;

import net.skoumal.forceupdate.OfflineHelper;
import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionResult;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by gingo on 20.11.2016.
 */
public class CarretoVersionProviderTest {

    @Test
    public void loadVersion() throws Exception {
        CarretoVersionProvider provider = new CarretoVersionProvider("com.vocabularyminer.android");

        VersionResult result = provider.getVersionResult();

        if(!OfflineHelper.ignoreOfflineErrors()) {
            assertFalse(result.isError());
            assertNotNull(result);
            assertNotNull(result.getVersion());
            assertNull(result.getErrorMessage());
            assertNull(result.getErrorException());

            assertEquals(result.getVersion().toString(), new Version("1.1.0").toString());
        }

    }

    @Test
    @SdkSuppress(minSdkVersion=19)
    public void loadVersionOffline() throws Exception {
        CarretoVersionProvider provider = new CarretoVersionProvider("com.vocabularyminer.android");

        TestButler.setGsmState(false);
        TestButler.setWifiState(false);

        try {
            VersionResult result = provider.getVersionResult();

            assertTrue(result.isError());
            assertNotNull(result);
            assertNull(result.getVersion());
            assertNotNull(result.getErrorMessage());
            assertNotNull(result.getErrorException());

        } finally {
            TestButler.setGsmState(true);
            TestButler.setWifiState(true);
            Thread.sleep(1000);
        }

    }

}
