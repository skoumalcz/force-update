package net.skoumal.forceupdate.provider;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionProvider;

/**
 * Created by gingo on 26.9.2016.
 */
public class ApkVersionProvider implements VersionProvider {

    private Version version;

    public ApkVersionProvider(Application gApplication) {
        PackageInfo pInfo = null;
        try {
            pInfo = gApplication.getPackageManager().getPackageInfo(gApplication.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        version = new Version(pInfo.versionName);
    }

    @Override
    public Version getVersion() {
        return version;
    }
}
