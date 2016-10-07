package net.skoumal.forceupdate.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gingo on 7.10.2016.
 */
public class Json {

    public static String getStringOrNull(JSONObject gJsonObject, String gAttribute) {
        if(gJsonObject.has(gAttribute)) {
            try {
                return gJsonObject.getString(gAttribute);
            } catch (JSONException e) {
                // should be covered by gJsonObject.has() condition :-)
                return null;
            }
        } else {
            return null;
        }
    }
}
