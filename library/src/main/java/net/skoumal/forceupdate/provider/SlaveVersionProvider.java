package net.skoumal.forceupdate.provider;

import net.skoumal.forceupdate.Version;
import net.skoumal.forceupdate.VersionProvider;
import net.skoumal.forceupdate.VersionResult;

import java.util.List;

/**
 * Created by gingo on 6.10.2016.
 */
public class SlaveVersionProvider implements VersionProvider {

    private MasterVersionProvider masterProvider;

    private VersionResult waitingResult = null;

    public SlaveVersionProvider(MasterVersionProvider gMasterProvider) {
        masterProvider = gMasterProvider;
    }

    @Override
    public VersionResult getVersion() {
        masterProvider.fetchVersions();

        return waitingResult;
    }

    public void putVersionResult(VersionResult gResult) {
        waitingResult = gResult;
    }

}
