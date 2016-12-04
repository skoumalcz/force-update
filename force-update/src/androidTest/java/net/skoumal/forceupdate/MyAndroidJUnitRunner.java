package net.skoumal.forceupdate;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnitRunner;

import com.linkedin.android.testbutler.TestButler;

/**
 * Created by gingo on 20.11.2016.
 */
public class MyAndroidJUnitRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext());
        super.onStart();
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext());
        super.finish(resultCode, results);
    }

}
