package net.skoumal.forceupdate.view.activity;

import android.app.Activity;
import android.content.Intent;

import net.skoumal.forceupdate.UpdateView;
import net.skoumal.forceupdate.Version;

public class ActivityUpdateView implements UpdateView {

    public static final String CURRENT_VERSION_EXTRA = "current_version";
    public static final String REQUIRED_VERSION_EXTRA = "required_version";
    public static final String PAYLOAD_EXTRA = "payload";

    private Class<?> activityClass;

    public ActivityUpdateView(Class<?> gActivityClass) {
        activityClass = gActivityClass;
    }

    @Override
    public void showView(Activity gActivity, Version gCurrentVersion, Version gRequiredVersion, String gPayload) {

        Intent intent = new Intent(gActivity, activityClass);

        intent.putExtra(CURRENT_VERSION_EXTRA, gCurrentVersion);
        intent.putExtra(REQUIRED_VERSION_EXTRA, gRequiredVersion);
        intent.putExtra(PAYLOAD_EXTRA, gPayload);

        gActivity.startActivity(intent);
    }
}
