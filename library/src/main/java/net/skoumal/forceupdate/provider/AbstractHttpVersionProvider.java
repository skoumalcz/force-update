package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.VersionProvider;
import net.skoumal.forceupdate.VersionResult;
import net.skoumal.forceupdate.util.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gingo on 26.9.2016.
 */
public abstract class AbstractHttpVersionProvider implements VersionProvider {

    private URL url;

    public AbstractHttpVersionProvider(String gUrl) {

        try {
            url = new URL(gUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VersionResult getVersion() {

        try {
            String stringResponse = Http.loadString(url);
            return processHttpResponse(stringResponse.toString());
        } catch (IOException e) {
            return new VersionResult(e.toString(), e);
        }

    }

    public String getUrl() {
        return url.toString();
    }

    protected abstract VersionResult processHttpResponse(String gResponseString);
}
