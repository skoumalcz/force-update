package net.skoumal.forceupdate.view.activity;

import android.app.Activity;
import android.content.Intent;

import net.skoumal.forceupdate.UpdateView;
import net.skoumal.forceupdate.Version;

/**
 * Created by gingo on 1.10.2016.
 */
public abstract class AbstractActivityUpdateView implements UpdateView {

    public static final String CURRENT_VERSION_EXTRA = "current_version";
    public static final String REQUIRED_VERSION_EXTRA = "required_version";
    public static final String UPDATE_MESSAGE_EXTRA = "update_message";

    private Class<?> activityClass;

    public AbstractActivityUpdateView(Class<?> gActivityClass) {
        activityClass = gActivityClass;
    }

    @Override
    public void showView(Activity gActivity, Version gCurrentVersion, Version gRequiredVersion, String gUpdateMessage) {

        Intent intent = new Intent(gActivity, activityClass);

        intent.putExtra(CURRENT_VERSION_EXTRA, gCurrentVersion);
        intent.putExtra(REQUIRED_VERSION_EXTRA, gRequiredVersion);
        intent.putExtra(UPDATE_MESSAGE_EXTRA, gUpdateMessage);

        gActivity.startActivity(intent);
    }
}
