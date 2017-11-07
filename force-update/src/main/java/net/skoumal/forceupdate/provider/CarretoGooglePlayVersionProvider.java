package net.skoumal.forceupdate.provider;

import android.app.Application;

/**
 * Load version from http://carreto.pt/tools/android-store-version/?package=<app-package>.
 *
 * Fully depends on this third party service to work properly!
 */
public class CarretoGooglePlayVersionProvider extends JsonHttpVersionProvider {

    private static final String URL_ADDRESS = "http://carreto.pt/tools/android-store-version/?package=";

    public CarretoGooglePlayVersionProvider(Application gApplication) {
        this(gApplication.getApplicationContext().getPackageName());
    }

    /**
     * Creates version provider directly for given package name.
     * @param gPackageName name of app package to load version from Google Play
     */
    public CarretoGooglePlayVersionProvider(String gPackageName) {
        super(URL_ADDRESS + gPackageName,
                "version", "last_version_description");
    }
}
