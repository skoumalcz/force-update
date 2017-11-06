package net.skoumal.forceupdate.util;

import android.content.Context;

import net.skoumal.forceupdate.VersionResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gingo on 7.10.2016.
 */
public class Http {

    private static final int HTTP_TIMEOUT = 10 * 1000;

    public static String loadString(URL gUrl) throws IOException {
        HttpURLConnection urlConnection = null;
        try {

            urlConnection = (HttpURLConnection) gUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(HTTP_TIMEOUT);
            urlConnection.setReadTimeout(HTTP_TIMEOUT);
            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            if(status >= 200 && status < 300) {

                //TODO [1] fix this to not add new line sign to last line
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                boolean firstLine = true;
                while ((line = responseReader.readLine()) != null) {
                    if(firstLine) {
                        firstLine = false;
                    } else {
                        response.append('\n');
                    }
                    response.append(line);
                }

                return response.toString();
            } else {
                throw new IOException("HTTP status code " + status + " returned.");
            }

        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
