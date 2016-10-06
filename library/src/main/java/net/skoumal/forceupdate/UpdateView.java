package net.skoumal.forceupdate;

import android.app.Activity;

/**
 * Created by gingo on 25.9.2016.
 */
public interface UpdateView {

    void showView(Activity gActivity, Version gCurrentVersion, Version gRequiredVersion, String gUpdateMessage);

}
