package net.skoumal.forceupdate.example.provider;

import android.content.SharedPreferences;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionProvider;
import net.skoumal.forceupdate.VersionResult;
import net.skoumal.forceupdate.util.Versions;

/**
 * Created by gingo on 3.1.2017.
 */
public class SharedPreferencesVersionProvider implements VersionProvider {

    private Version version;
    private SharedPreferences sharedPreferences;
    private String sharedPreferencesKey;
    private Version defaultVersion;

    public SharedPreferencesVersionProvider(SharedPreferences gSharedPreferences,
                                            String gKey,
                                            Version gDefaultVersion) {
        sharedPreferences = gSharedPreferences;
        sharedPreferencesKey = gKey;
        defaultVersion = gDefaultVersion;

        String versionStr = sharedPreferences.getString(sharedPreferencesKey, null);
        if(versionStr == null) {
            version = gDefaultVersion;
        } else {
            version = new Version(versionStr);
        }
    }

    @Override
    public VersionResult getVersionResult() {
        return new VersionResult(version, null);
    }

    public void incrementVersion() {
        int[] versionParts = version.getVersionParts();

        Versions.incrementVersion(versionParts);

        version = new Version(versionParts);

        saveVersion();
    }

    public void decrementVersion() {
        int[] versionParts = version.getVersionParts();

        Versions.decrementVersion(versionParts);

        version = new Version(versionParts);

        saveVersion();
    }

    public void resetVersion() {
        version = defaultVersion;
        sharedPreferences.edit().remove(sharedPreferencesKey).apply();
    }

    private void saveVersion() {
        sharedPreferences.edit().putString(sharedPreferencesKey, version.toString()).apply();
    }
}
