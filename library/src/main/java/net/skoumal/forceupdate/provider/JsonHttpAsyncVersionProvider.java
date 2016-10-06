package net.skoumal.forceupdate.provider;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import net.skoumal.forceupdate.ForceUpdateLogger;
import net.skoumal.forceupdate.Version;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gingo on 26.9.2016.
 */
public class JsonHttpAsyncVersionProvider extends AbstractHttpAsyncVersionProvider {

    private String versionAttribute = "version";
    private String descriptionAttribute = "version_description";

    public JsonHttpAsyncVersionProvider(String gUrl) {
        super(gUrl);
    }

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

            String versionString = getStringOrNull(mainObject, versionAttribute);
            String versionDescription = getStringOrNull(mainObject, descriptionAttribute);

            if(!TextUtils.isEmpty(versionString)) {
                gResult.version(new Version(versionString), versionDescription);
            } else {
                gResult.error("Attribute is empty \"" + versionAttribute + "\" or not present in JSON response.");
            }

        } catch (JSONException e) {
            String errorMessage = "Error when parsing json from " + getUrl();
            if(ForceUpdateLogger.isWarnEnabled()) {
                ForceUpdateLogger.w(errorMessage, e);
            }
            gResult.error(errorMessage);
        }
    }

    private String getStringOrNull(JSONObject gJsonObject, String gAttribute) {
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
