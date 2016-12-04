package net.skoumal.forceupdate.view.activity;

import android.os.Bundle;

/**
 * Created by gingo on 7.10.2016.
 */
public class RecommendedUpdateActivity extends ForceUpdateActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(android.R.style.Theme_Dialog);
    }
}
