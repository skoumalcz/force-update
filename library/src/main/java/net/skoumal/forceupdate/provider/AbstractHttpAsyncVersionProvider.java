package net.skoumal.forceupdate.provider;

import android.app.Application;

import net.skoumal.forceupdate.AsyncVersionProvider;
import net.skoumal.forceupdate.Version;

import org.json.JSONException;
import org.json.JSONObject;

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
public abstract class AbstractHttpAsyncVersionProvider implements AsyncVersionProvider {

    private static final int HTTP_TIMEOUT = 10 * 1000;

    private URL url;

    public AbstractHttpAsyncVersionProvider(String gUrl) {

        try {
            url = new URL(gUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getVersion(final VersionProviderResult gResult) {
        new Thread(new Runnable() {

            @Override
            public void run() {


                HttpURLConnection urlConnection = null;
                try {

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.setConnectTimeout(HTTP_TIMEOUT);
                    urlConnection.setReadTimeout(HTTP_TIMEOUT);
                    urlConnection.connect();

                    int status = urlConnection.getResponseCode();
                    if(status >= 200 && status < 300) {

                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = responseReader.readLine()) != null) {
                            response.append(line).append('\n');
                        }

                        processHttpResponse(response.toString(), gResult);
                    } else {
                        // simply send empty result
                        gResult.version(null, null);
                    }

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    // simply send empty result
                    gResult.version(null, null);
                } finally {
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }

        }).start();
    }

    public String getUrl() {
        return url.toString();
    }

    protected abstract void processHttpResponse(String gResponseString, VersionProviderResult gResult);
}
