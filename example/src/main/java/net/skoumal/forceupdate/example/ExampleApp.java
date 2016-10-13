package net.skoumal.forceupdate.example;

import android.app.Activity;
import android.app.Application;

import net.skoumal.forceupdate.ForceUpdate;
import net.skoumal.forceupdate.UpdateView;
import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.example.activites.CustomForceUpdateActivity;
import net.skoumal.forceupdate.provider.ApkVersionProvider;
import net.skoumal.forceupdate.provider.JsonHttpAsyncVersionProvider;

/**
 * Created by Tadeas on 27.09.2016.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JsonHttpAsyncVersionProvider forcedVersionProvider
                = new JsonHttpAsyncVersionProvider("http://version.skoumal.net/forceupdate/version.json", "mandatory_version", "description");

        new ForceUpdate.Builder()
                .application(this)
                .currentVersionProvider(new ApkVersionProvider(this))
                .minAllowedVersionProvider(forcedVersionProvider)
                .addForceUpdateActivity(CustomForceUpdateActivity.class)
                .forcedUpdateView(new UpdateView() {
                    @Override
                    public void showView(Activity gActivity, Version gCurrentVersion, Version gRequiredVersion, String gUpdateMessage) {
                        CustomForceUpdateActivity.start(gActivity, gCurrentVersion.toString(), gRequiredVersion.toString(), gUpdateMessage);
                    }
                })
                .buildAndInit();
    }
}
