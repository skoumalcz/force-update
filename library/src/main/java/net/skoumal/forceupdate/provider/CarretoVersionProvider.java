package net.skoumal.forceupdate.provider;

import android.app.Application;

/**
 * Created by gingo on 26.9.2016.
 */
public class CarretoVersionProvider extends JsonHttpAsyncVersionProvider {

    private static final String URL_ADDRESS = "http://carreto.pt/tools/android-store-version/?package=";

    public CarretoVersionProvider(Application gApplication) {
        super(URL_ADDRESS + gApplication.getApplicationContext().getPackageName(),
                "version", "last_version_description");
    }

}
