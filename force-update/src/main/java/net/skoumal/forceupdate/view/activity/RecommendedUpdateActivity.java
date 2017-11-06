package net.skoumal.forceupdate.view.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import net.skoumal.forceupdate.R;

/**
 * Created by gingo on 7.10.2016.
 */
public class RecommendedUpdateActivity extends ForceUpdateActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState, R.style.NoActionBarDialogTheme);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        setFinishOnTouchOutside(false);
    }
}
