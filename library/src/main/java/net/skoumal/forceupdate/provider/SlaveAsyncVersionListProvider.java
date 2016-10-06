package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.AsyncVersionListProvider;
import net.skoumal.forceupdate.Version;

import java.util.List;

/**
 * Created by gingo on 6.10.2016.
 */
public class SlaveAsyncVersionListProvider implements AsyncVersionListProvider {

    private MasterVersionProvider masterProvider;

    private VersionListProviderResult waitingResult = null;

    public SlaveAsyncVersionListProvider(MasterVersionProvider gMasterProvider) {
        masterProvider = gMasterProvider;
    }

    @Override
    public void getVersionList(VersionListProviderResult gResult) {

        waitingResult = gResult;

        masterProvider.fetchVersions();
    }

    public void putVersionList(List<Version> gVersion, List<String> gPayload) {
        if(waitingResult != null) {
            waitingResult.versionList(gVersion, gPayload);
            waitingResult = null;
        }
    }

}
