package net.skoumal.forceupdate.example.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.processphoenix.ProcessPhoenix;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.example.ExampleApp;
import net.skoumal.forceupdate.example.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showVersions();

        initPlusMinusButtonsListener();

        findViewById(R.id.restart_app_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessPhoenix.triggerRebirth(MainActivity.this);
            }
        });
    }

    private void initPlusMinusButtonsListener() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusMinusButtonClicked(v.getId());
            }
        };

        findViewById(R.id.excluded_version_plus_button).setOnClickListener(clickListener);
        findViewById(R.id.excluded_version_minus_button).setOnClickListener(clickListener);
        findViewById(R.id.min_allowed_version_plus_button).setOnClickListener(clickListener);
        findViewById(R.id.min_allowed_version_minus_button).setOnClickListener(clickListener);
        findViewById(R.id.recommended_version_plus_button).setOnClickListener(clickListener);
        findViewById(R.id.recommended_version_minus_button).setOnClickListener(clickListener);
    }

    private void showVersions() {
        ExampleApp app = ExampleApp.getInstance();

        showVersion(R.id.current_version_value,
                app.getCurrentVersionProvider().getVersionResult().getVersion());
        showVersion(R.id.recommended_version_value,
                app.getRecommendedVersionProvider().getVersionResult().getVersion());
        showVersion(R.id.min_allowed_version_value,
                app.getMinAllowedVersionProvider().getVersionResult().getVersion());
        showVersion(R.id.excluded_version_value,
                app.getExcludedVersionProvider().getVersionResult().getVersion());
    }

    private void showVersion(int gTextViewId, Version gVersion) {
        TextView textView = (TextView)findViewById(gTextViewId);
        textView.setText(gVersion.toString());
    }

    private void plusMinusButtonClicked(int gViewId) {
        ExampleApp app = ExampleApp.getInstance();

        switch (gViewId) {
            case R.id.excluded_version_plus_button:
                app.getExcludedVersionProvider().incrementVersion();
                break;
            case R.id.excluded_version_minus_button:
                app.getExcludedVersionProvider().decrementVersion();
                break;
            case R.id.min_allowed_version_plus_button:
                app.getMinAllowedVersionProvider().incrementVersion();
                break;
            case R.id.min_allowed_version_minus_button:
                app.getMinAllowedVersionProvider().decrementVersion();
                break;
            case R.id.recommended_version_plus_button:
                app.getRecommendedVersionProvider().incrementVersion();
                break;
            case R.id.recommended_version_minus_button:
                app.getRecommendedVersionProvider().decrementVersion();
                break;
            default:
                throw new RuntimeException("Unexpected View ID.");
        }

        showVersions();
    }

}
