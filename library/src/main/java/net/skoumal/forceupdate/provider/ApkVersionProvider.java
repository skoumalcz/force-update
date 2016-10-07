package net.skoumal.forceupdate.provider;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionProvider;
import net.skoumal.forceupdate.VersionResult;

/**
 * Created by gingo on 26.9.2016.
 */
public class ApkVersionProvider implements VersionProvider {

    private VersionResult versionResult;

    public ApkVersionProvider(Application gApplication) {
        PackageInfo pInfo = null;
        try {
            pInfo = gApplication.getPackageManager().getPackageInfo(gApplication.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        Version version = new Version(pInfo.versionName);

        versionResult = new VersionResult(version, null);
    }

    @Override
    public VersionResult getVersion() {
        return versionResult;
    }
}
