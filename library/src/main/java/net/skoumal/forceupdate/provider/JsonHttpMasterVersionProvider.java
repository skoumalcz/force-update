package net.skoumal.forceupdate.provider;

import android.text.TextUtils;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionResult;
import net.skoumal.forceupdate.util.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gingo on 7.10.2016.
 */
public class JsonHttpMasterVersionProvider extends AbstractHttpMasterVersionProvider {

    private static String EXCLUDED_VERSION_ATTRIBUTE = "version";
    private static String EXCLUDED_PAYLOAD_ATTRIBUTE = "payload";

    private String recommendedVersionAttribute = "recommended_version";
    private String recommendedPayloadAttribute = "recommended_payload";

    private String minAllowedVersionAttribute = "min_allowed_version";
    private String minAllowedPayloadAttribute = "min_allowed_payload";

    private String excludedVersionsAttribute = "excluded_versions";

    public JsonHttpMasterVersionProvider(String gUrl) {
        super(gUrl);
    }

    public JsonHttpMasterVersionProvider(String gUrl,
                 String gRecommendedVersionAttribute, String gRecommendedPayloadAttribute,
                 String gMinAllowedVersionAttribute, String gMinAllowedPayloadAttribute,
                 String gExcludedVersionsAttribute) {
        super(gUrl);
        recommendedVersionAttribute = gRecommendedVersionAttribute;
        recommendedPayloadAttribute = gRecommendedPayloadAttribute;

        minAllowedVersionAttribute = gMinAllowedVersionAttribute;
        minAllowedPayloadAttribute = gMinAllowedPayloadAttribute;

        excludedVersionsAttribute = gExcludedVersionsAttribute;
    }

    @Override
    protected void processHttpResponseAndPutVersions(String gResponseString) {
        try {
            JSONObject mainObject = new JSONObject(gResponseString);

            VersionResult recommendedResult = getVersionResultOrNull(mainObject, recommendedVersionAttribute, recommendedPayloadAttribute);
            if(recommendedResult != null) {
                putRecommendedVersionResult(recommendedResult);
            }

            VersionResult minAllowedResult = getVersionResultOrNull(mainObject, minAllowedVersionAttribute, minAllowedPayloadAttribute);
            if(minAllowedResult != null) {
                putMinAllowedVersion(minAllowedResult);
            }

            if(mainObject.has(excludedVersionsAttribute)) {
                JSONArray excludedArray = mainObject.getJSONArray(excludedVersionsAttribute);

                List<Version> versionList = new ArrayList<>();
                List<String> payloadList = new ArrayList<>();

                for(int i = 0; i < excludedArray.length(); i++) {
                    JSONObject versionObject = excludedArray.getJSONObject(i);

                    Version version = getVersionOrNull(versionObject, EXCLUDED_VERSION_ATTRIBUTE);
                    if(version != null) {
                        versionList.add(version);
                        payloadList.add(Json.getStringOrNull(versionObject, EXCLUDED_PAYLOAD_ATTRIBUTE));
                    }
                }

                putExcludedVersionList(new VersionResult(versionList, payloadList));
            }

        } catch (JSONException e) {
            String errorMessage = "Error when parsing json from " + getUrl();

            VersionResult result = new VersionResult(errorMessage, e);
            putExcludedVersionList(result);
            putMinAllowedVersion(result);
            putRecommendedVersionResult(result);
        }
    }

    private Version getVersionOrNull(JSONObject gJsonObject, String gVersionAttribute) {
        String versionString = Json.getStringOrNull(gJsonObject, gVersionAttribute);

        if(!TextUtils.isEmpty(versionString)) {
            return new Version(versionString);
        } else {
            return null;
        }
    }

    private VersionResult getVersionResultOrNull(JSONObject gJsonObject, String gVersionAttribute, String gPayloadAttribute) {
        Version version = getVersionOrNull(gJsonObject, gVersionAttribute);
        String payloadString = Json.getStringOrNull(gJsonObject, gPayloadAttribute);

        if(version != null) {
            return new VersionResult(version, payloadString);
        } else {
            return null;
        }
    }
}
