package net.skoumal.forceupdate.example;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import net.skoumal.forceupdate.ForceUpdate;
import net.skoumal.forceupdate.UpdateView;
import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionProvider;
import net.skoumal.forceupdate.example.activites.CustomForceUpdateActivity;
import net.skoumal.forceupdate.example.activites.ResetVersionsForceUpdateActivity;
import net.skoumal.forceupdate.example.provider.SharedPreferencesVersionProvider;
import net.skoumal.forceupdate.provider.ApkVersionProvider;
import net.skoumal.forceupdate.util.Versions;
import net.skoumal.forceupdate.view.activity.ActivityUpdateView;
import net.skoumal.forceupdate.view.activity.RecommendedUpdateActivity;

/**
 * Created by Tadeas on 27.09.2016.
 */

public class ExampleApp extends Application {

    private final static boolean SHOW_CUSTOM_FORCED_VIEW = false;

    private static ExampleApp instance;

    private VersionProvider currentVersionProvider;
    private SharedPreferencesVersionProvider minAllowedVersionProvider;
    private SharedPreferencesVersionProvider recommendedVersionProvider;
    private SharedPreferencesVersionProvider excludedVersionProvider;

    private ForceUpdate forceUpdate;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_VERSIONS,
                MODE_PRIVATE);

        /*
            -- Current version --

            The simples way to provide current version is via ApkVersionProvider, but you can
            implement your own provider of course.
          */
        currentVersionProvider = new ApkVersionProvider(this);

        /*
            -- Min allowed and recommended version --

            For this example purposes we use SharedPreferencesVersionProvider to fake min-allowed
            and recommended version. It is also great example of custom VersionProvider, but for
            real world purposes see use one of ready-to-use VersionProviders, or implement your own:

            https://github.com/skoumalcz/force-update/blob/master/doc/VersionProviders.md
         */
        Version defaultVersion = currentVersionProvider.getVersionResult().getVersion(); // default version when SharedPreferences are empty
        minAllowedVersionProvider = new SharedPreferencesVersionProvider(preferences, Constants.SHARED_PREFERENCES_MIN_ALLOWED_VERSION, defaultVersion);
        recommendedVersionProvider = new SharedPreferencesVersionProvider(preferences, Constants.SHARED_PREFERENCES_RECOMMENDED_VERSION, defaultVersion);

        /*
            -- Excluded version --

            To ban particular version we also use SharedPreferencesVersionProvider to fake it.
         */
        int [] apkVersionParts = currentVersionProvider.getVersionResult().getVersion().getVersionParts();
        Versions.decrementVersion(apkVersionParts);
        excludedVersionProvider = new SharedPreferencesVersionProvider(preferences, Constants.SHARED_PREFERENCES_EXCLUDED_VERSION, new Version(apkVersionParts));

        ForceUpdate.Builder builder = new ForceUpdate.Builder()
                .application(this)
                .debug(true)
                .currentVersionProvider(currentVersionProvider)
                .minAllowedVersionProvider(minAllowedVersionProvider)
                .recommendedVersionProvider(recommendedVersionProvider)
                .excludedVersionListProvider(excludedVersionProvider)
                .minAllowedVersionCheckMinInterval(1)
                .recommendedVersionCheckMinInterval(1)
                .excludedVersionListCheckMinInterval(1)
                .forcedUpdateView(new ActivityUpdateView(ResetVersionsForceUpdateActivity.class))
                .recommendedUpdateView(new ActivityUpdateView(RecommendedUpdateActivity.class));

        if (SHOW_CUSTOM_FORCED_VIEW) {
            //here you can show your custom activity or just exclude forcedUpdateView function to use default activity
            builder.addForceUpdateActivity(CustomForceUpdateActivity.class);
            builder.forcedUpdateView(new UpdateView() {
                @Override
                public void showView(Activity gActivity, Version gCurrentVersion, Version gRequiredVersion, String gUpdateMessage) {
                    CustomForceUpdateActivity.start(gActivity, gCurrentVersion.toString(), gRequiredVersion.toString(), gUpdateMessage);
                }
            });
        }

        forceUpdate = builder.buildAndInit();
    }

    public static ExampleApp getInstance() {
        return instance;
    }

    public VersionProvider getCurrentVersionProvider() {
        return currentVersionProvider;
    }

    public SharedPreferencesVersionProvider getMinAllowedVersionProvider() {
        return minAllowedVersionProvider;
    }

    public SharedPreferencesVersionProvider getRecommendedVersionProvider() {
        return recommendedVersionProvider;
    }

    public SharedPreferencesVersionProvider getExcludedVersionProvider() {
        return excludedVersionProvider;
    }

    public ForceUpdate getForceUpdate() {
        return forceUpdate;
    }
}
