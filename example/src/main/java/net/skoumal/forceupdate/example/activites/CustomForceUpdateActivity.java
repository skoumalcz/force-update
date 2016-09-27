package net.skoumal.forceupdate.example.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import net.skoumal.forceupdate.example.R;

/**
 * Created by Tadeas on 27.09.2016.
 */

public class CustomForceUpdateActivity extends Activity {
    private static final String EXTRA_ACTUAL_VERSION = "extra_actual_version";
    private static final String EXTRA_FORCED_VERSION = "extra_forced_version";
    private static final String EXTRA_FORCED_VERSION_DESCRIPTION = "extra_forced_version_description";


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_forced_update);
    }

    public static void start(Context context, String actualVersion, String forcedVersion, String forcedVersionDescription) {
        Intent starter = new Intent(context, CustomForceUpdateActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        starter.putExtra(EXTRA_ACTUAL_VERSION, actualVersion);
        starter.putExtra(EXTRA_FORCED_VERSION, forcedVersion);
        starter.putExtra(EXTRA_FORCED_VERSION_DESCRIPTION, forcedVersionDescription);

        context.startActivity(starter);
    }
}
