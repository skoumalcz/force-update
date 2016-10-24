package net.skoumal.forceupdate.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.skoumal.forceupdate.R;
import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.util.GooglePlay;
import net.skoumal.forceupdate.util.Http;

/**
 * Created by gingo on 1.10.2016.
 */
public class ForceUpdateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.NoActionBarTheme);

        setContentView(R.layout.force_update_activity);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGooglePlay();
            }
        });

    }

    public void openGooglePlay() {
        GooglePlay.openAppDetail(this);
    }

    public static void start(Activity gActivity) {
        Intent starter = new Intent(gActivity, ForceUpdateActivity.class);
        gActivity.startActivity(starter);
    }
}
