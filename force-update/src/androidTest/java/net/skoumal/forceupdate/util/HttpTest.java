package net.skoumal.forceupdate.util;

import android.text.TextUtils;

import net.skoumal.forceupdate.OfflineHelper;
import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionResult;
import net.skoumal.forceupdate.provider.JsonHttpVersionProvider;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gingo on 4.12.2016.
 */
public class HttpTest {

    @Test
    public void loadLoadStringWithNullUrl() throws Exception {
        try {
            Http.loadString(null);
            fail("Should throw NullPointerException.");
        } catch (NullPointerException e) {
            // great!
        }
    }

    @Test
    public void loadLoadString200() throws Exception {
        String result = Http.loadString(new URL("https://raw.githubusercontent.com/skoumalcz/force-update/master/force-update/src/androidTest/java/net/skoumal/forceupdate/util/HttpTest.txt"));

        assertFalse(TextUtils.isEmpty(result));
        assertTrue(result.contains("Hello!"));
        assertTrue(result.contains("Bye ..."));
        assertEquals(result, "Hello!\n\nBye ...");
    }

    @Test
    public void loadLoadString404() throws Exception {
        try {
            Http.loadString(new URL("http://www.skoumal.net/B4aeRRq23XOCTwRBTrnv"));
            fail("Should throw IOException.");
        } catch (IOException e) {
            // TODO [1] check IOException type
            // great!
        }
    }
}
