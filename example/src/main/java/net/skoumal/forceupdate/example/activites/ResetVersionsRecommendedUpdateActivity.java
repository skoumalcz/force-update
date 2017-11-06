package net.skoumal.forceupdate.example.activites;

import android.os.Bundle;
import android.view.View;

import com.jakewharton.processphoenix.ProcessPhoenix;

import net.skoumal.forceupdate.example.ExampleApp;
import net.skoumal.forceupdate.view.activity.ForceUpdateActivity;

/**
 * Created by gingo on 3.1.2017.
 */
public class ResetVersionsRecommendedUpdateActivity extends ForceUpdateActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(net.skoumal.forceupdate.R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleApp.getInstance().getMinAllowedVersionProvider().resetVersion();
                ExampleApp.getInstance().getExcludedVersionProvider().resetVersion();
                ExampleApp.getInstance().getRecommendedVersionProvider().resetVersion();

                ProcessPhoenix.triggerRebirth(ResetVersionsRecommendedUpdateActivity.this);
            }
        });

    }
}
