package net.skoumal.forceupdate.provider;

import android.app.Application;
import android.util.Log;

import net.skoumal.forceupdate.ForceUpdateLogger;
import net.skoumal.forceupdate.Version;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gingo on 26.9.2016.
 */
public class JsonHttpAsyncVersionProvider extends AbstractHttpAsyncVersionProvider {

    private String versionAttribute;
    private String descriptionAttribute;

    public JsonHttpAsyncVersionProvider(String gUrl, String gVersionAttribute, String gDescriptionAttribute) {
        super(gUrl);

        versionAttribute = gVersionAttribute;
        descriptionAttribute = gDescriptionAttribute;
    }

    @Override
    protected void processHttpResponse(String gResponseString, VersionProviderResult gResult) {
        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(gResponseString);

            String versionString = mainObject.getString(versionAttribute);
            String versionDescription = mainObject.getString(descriptionAttribute);

            gResult.version(new Version(versionString), versionDescription);

        } catch (JSONException e) {
            if(ForceUpdateLogger.isWarnEnabled()) {
                ForceUpdateLogger.w("Error when parsing json from " + getUrl(), e);
            }
            gResult.version(null, null);
        }
    }
}
