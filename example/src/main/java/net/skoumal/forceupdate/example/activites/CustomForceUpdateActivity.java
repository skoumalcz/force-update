package net.skoumal.forceupdate.example.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import net.skoumal.forceupdate.example.R;

/**
 * Created by Tadeas on 27.09.2016.
 */

public class CustomForceUpdateActivity extends Activity {
    private static final String EXTRA_ACTUAL_VERSION = "extra_actual_version";
    private static final String EXTRA_FORCED_VERSION = "extra_forced_version";
    private static final String EXTRA_FORCED_VERSION_DESCRIPTION = "extra_forced_version_description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forced_update);

        TextView updateDescriptionTextview = (TextView) findViewById(R.id.textview_force_update_description);
        updateDescriptionTextview.setText(getIntent().getExtras().getString(EXTRA_FORCED_VERSION_DESCRIPTION));
    }

    public static void start(Activity activity, String actualVersion, String forcedVersion, String forcedVersionDescription) {
        Intent starter = new Intent(activity, CustomForceUpdateActivity.class);

        starter.putExtra(EXTRA_ACTUAL_VERSION, actualVersion);
        starter.putExtra(EXTRA_FORCED_VERSION, forcedVersion);
        starter.putExtra(EXTRA_FORCED_VERSION_DESCRIPTION, forcedVersionDescription);

        activity.startActivity(starter);
    }
}
