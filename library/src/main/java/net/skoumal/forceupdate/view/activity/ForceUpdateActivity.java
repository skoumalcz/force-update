package net.skoumal.forceupdate.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.skoumal.forceupdate.R;
import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.util.Http;

/**
 * Created by gingo on 1.10.2016.
 */
public class ForceUpdateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.force_update_activity);

        //TODO [1] disable status bar

    }

    public void openGooglePlay() {
        String url = Http.getGooglePlayUrl(this);

        //TODO [1] open Google Play
    }
}
