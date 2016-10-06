package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.AsyncVersionProvider;
import net.skoumal.forceupdate.Version;

/**
 * Created by gingo on 6.10.2016.
 */
public class SlaveAsyncVersionProvider implements AsyncVersionProvider {

    private MasterVersionProvider masterProvider;

    private VersionProviderResult waitingResult = null;

    public SlaveAsyncVersionProvider(MasterVersionProvider gMasterProvider) {
        masterProvider = gMasterProvider;
    }

    @Override
    public void getVersion(VersionProviderResult gResult) {

        waitingResult = gResult;

        masterProvider.fetchVersions();
    }

    public void putVersion(Version gVersion, String gPayload) {
        if(waitingResult != null) {
            waitingResult.version(gVersion, gPayload);
            waitingResult = null;
        }
    }
}
