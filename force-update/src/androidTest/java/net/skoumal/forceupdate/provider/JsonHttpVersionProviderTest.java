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
 * Created by gingo on 21.11.2016.
 */
public class JsonHttpVersionProviderTest {

    @Test
    public void loadVersionDefaultAttributes() throws Exception {
        JsonHttpVersionProvider provider = new JsonHttpVersionProvider("https://raw.githubusercontent.com/skoumalcz/force-update/master/force-update/src/androidTest/java/net/skoumal/forceupdate/provider/JsonHttpVersionProviderTest.DefaultAttributes.json");

        VersionResult result = provider.getVersion();

        if(!OfflineHelper.ignoreOfflineErrors()) {
            assertFalse(result.isError());
            assertNotNull(result);
            assertNotNull(result.getVersion());
            assertNotNull(result.getPayload());
            assertNull(result.getErrorMessage());
            assertNull(result.getErrorException());

            assertEquals(result.getVersion().toString(), new Version("3.2.1").toString());
            assertEquals(result.getPayload(), "cool-payload");
        }

    }

    @Test
    public void loadVersionCustomAttributes() throws Exception {
        JsonHttpVersionProvider provider = new JsonHttpVersionProvider(
                "https://raw.githubusercontent.com/skoumalcz/force-update/master/force-update/src/androidTest/java/net/skoumal/forceupdate/provider/JsonHttpVersionProviderTest.CustomAttributes.json",
                "my_great_version",
                "see-my-payload");

        VersionResult result = provider.getVersion();

        if(!OfflineHelper.ignoreOfflineErrors()) {
            assertFalse(result.isError());
            assertNotNull(result);
            assertNotNull(result.getVersion());
            assertNotNull(result.getPayload());
            assertNull(result.getErrorMessage());
            assertNull(result.getErrorException());

            assertEquals(result.getVersion().toString(), new Version("3.2.1").toString());
            assertEquals(result.getPayload(), "cool-payload");
        }

    }

    @Test
    @SdkSuppress(minSdkVersion=19)
    public void loadVersionOffline() throws Exception {
        JsonHttpVersionProvider provider = new JsonHttpVersionProvider("https://raw.githubusercontent.com/skoumalcz/force-update/master/force-update/src/androidTest/java/net/skoumal/forceupdate/provider/JsonHttpVersionProviderTest.DefaultAttributes.json");

        TestButler.setGsmState(false);
        TestButler.setWifiState(false);

        try {
            VersionResult result = provider.getVersion();

            assertTrue(result.isError());
            assertNotNull(result);
            assertNull(result.getVersion());
            assertNull(result.getPayload());
            assertNotNull(result.getErrorMessage());
            assertNotNull(result.getErrorException());

        } finally {
            TestButler.setGsmState(true);
            TestButler.setWifiState(true);
            Thread.sleep(1000);
        }

    }

}
