package net.skoumal.forceupdate.provider;

import android.text.TextUtils;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionResult;
import net.skoumal.forceupdate.util.Json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gingo on 26.9.2016.
 */
public class JsonHttpVersionProvider extends AbstractHttpVersionProvider {

    private String versionAttribute = "version";
    private String payloadAttribute = "payload";

    public JsonHttpVersionProvider(String gUrl) {
        super(gUrl);
    }

    public JsonHttpVersionProvider(String gUrl, String gVersionAttribute, String gPayloadAttribute) {
        super(gUrl);

        versionAttribute = gVersionAttribute;
        payloadAttribute = gPayloadAttribute;
    }

    @Override
    protected VersionResult processHttpResponse(String gResponseString) {
        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(gResponseString);

            String version = Json.getStringOrNull(mainObject, versionAttribute);
            String payload = Json.getStringOrNull(mainObject, payloadAttribute);

            if(!TextUtils.isEmpty(version)) {
                return new VersionResult(new Version(version), payload);
            } else {
                return new VersionResult("Attribute is empty \"" + versionAttribute + "\" or not present in JSON response.", null);
            }

        } catch (JSONException e) {
            String errorMessage = "Error when parsing json from " + getUrl();

            return new VersionResult(errorMessage, e);
        }
    }

}
