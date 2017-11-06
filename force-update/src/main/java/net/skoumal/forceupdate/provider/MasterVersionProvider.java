package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.VersionResult;

/**
 * Created by gingo on 6.10.2016.
 */
public abstract class MasterVersionProvider {

    private SlaveVersionProvider recommendedProvider;

    private SlaveVersionProvider minAllowedProvider;

    private SlaveVersionProvider excludedProvider;

    private boolean fetchInProgress = false;

    public void fetchVersions() {

        synchronized (this) {
            fetchAndPutVersions();
        }

    }

    public void putRecommendedVersionResult(VersionResult gResult) {
        if(recommendedProvider != null) {
            recommendedProvider.putVersionResult(gResult);
        }
    }

    public void putMinAllowedVersion(VersionResult gResult) {
        if(minAllowedProvider != null) {
            minAllowedProvider.putVersionResult(gResult);
        }
    }

    public void putExcludedVersionList(VersionResult gResult) {
        if(excludedProvider != null) {
            excludedProvider.putVersionResult(gResult);
        }
    }

    public void setExcludedProvider(SlaveVersionProvider gExcludedProvider) {
        excludedProvider = gExcludedProvider;
    }

    public void setMinAllowedProvider(SlaveVersionProvider gMinAllowedProvider) {
        minAllowedProvider = gMinAllowedProvider;
    }

    public void setRecommendedProvider(SlaveVersionProvider gRecommendedProvider) {
        recommendedProvider = gRecommendedProvider;
    }

    protected abstract void fetchAndPutVersions();

}
