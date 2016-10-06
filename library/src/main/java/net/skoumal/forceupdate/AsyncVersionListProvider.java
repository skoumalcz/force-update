package net.skoumal.forceupdate;

import java.util.List;

/**
 * Created by gingo on 25.9.2016.
 */
public interface AsyncVersionListProvider {

    void getVersionList(VersionListProviderResult gResult);

    interface VersionListProviderResult {

        void versionList(List<Version> gVersionList, List<String> gPayloadList);

    }

}
