package net.skoumal.forceupdate.util;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Created by gingo on 4.12.2016.
 */
public class JsonTest {

    @Test
    public void normalTest() throws Exception
    {
        JSONObject object = new JSONObject("{\n" +
                "\t\"test01\": \"result01\"\n" +
                "}");

        assertEquals(Json.getStringOrNull(object, "test01"), "result01");
        assertNull(Json.getStringOrNull(object, "test02"));
    }
}
