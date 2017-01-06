package net.skoumal.forceupdate.util;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


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
    }
    @Test
    public void returnNullTest() throws Exception
    {
        JSONObject object = new JSONObject("{\n" +
                "\t\"test01\": \"result01\"\n" +
                "}");
        assertNull(Json.getStringOrNull(object, "test02"));
    }
    @Test
    public void nullObjectTest() throws Exception
    {
        JSONObject object = null;
        Json.getStringOrNull(object, "test");
        fail("Should throw NullPointerException");
    }
    @Test
    public void nestingTest() throws Exception
    {
        JSONObject object = new JSONObject("{\n" +
                "\t\"first\": {\n" +
                "\t\t\"second\": \"test\"\n" +
                "\t}\n" +
                "}");
        assertEquals("test", Json.getStringOrNull(new JSONObject(Json.getStringOrNull(object, "first")), "second"));
    }

}
