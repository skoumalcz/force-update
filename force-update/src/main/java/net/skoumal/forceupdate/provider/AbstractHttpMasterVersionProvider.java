package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.VersionResult;
import net.skoumal.forceupdate.util.Http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gingo on 6.10.2016.
 */
public abstract class AbstractHttpMasterVersionProvider extends MasterVersionProvider {

    private URL url;

    public AbstractHttpMasterVersionProvider(String gUrl) {

        try {
            url = new URL(gUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url.toString();
    }

    protected abstract void processHttpResponseAndPutVersions(String gResponseString);

    @Override
    protected void fetchAndPutVersions() {
        try {
            String stringResponse = Http.loadString(url);
            processHttpResponseAndPutVersions(stringResponse.toString());
        } catch (IOException e) {
            VersionResult versionResult = new VersionResult(e.toString(), e);

            putMinAllowedVersion(versionResult);
            putRecommendedVersionResult(versionResult);
            putExcludedVersionList(versionResult);
        }
    }

}
