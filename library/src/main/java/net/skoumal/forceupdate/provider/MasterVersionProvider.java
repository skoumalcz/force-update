package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.provider.SlaveAsyncVersionListProvider;
import net.skoumal.forceupdate.provider.SlaveAsyncVersionProvider;

import java.util.List;

/**
 * Created by gingo on 6.10.2016.
 */
public abstract class MasterVersionProvider {

    private SlaveAsyncVersionProvider recommendedProvider;

    private SlaveAsyncVersionProvider minAllowedProvider;

    private SlaveAsyncVersionListProvider excludedProvider;

    private boolean fetchInProgress = false;

    public void fetchVersions() {

        if(fetchInProgress) {
            return;
        }

        synchronized (this) {
            if(fetchInProgress) {
                return;
            } else {
                fetchInProgress = true;
            }
        }

        fetchAndPutVersions();

        fetchInProgress = false;
    }

    public void putRecommendedVersion(Version gVersion, String gPayload) {
        if(recommendedProvider != null) {
            recommendedProvider.putVersion(gVersion, gPayload);
        }
    }

    public void putMinAllowedVersion(Version gVersion, String gPayload) {
        if(minAllowedProvider != null) {
            minAllowedProvider.putVersion(gVersion, gPayload);
        }
    }

    public void putExcludedVersionList(List<Version> gVersionList, List<String> gPayloadList) {
        if(excludedProvider != null) {
            excludedProvider.putVersionList(gVersionList, gPayloadList);
        }
    }

    protected abstract void fetchAndPutVersions();


}
