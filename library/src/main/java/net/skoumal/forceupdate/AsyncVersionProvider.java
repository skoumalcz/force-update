package net.skoumal.forceupdate;

/**
 * Created by gingo on 25.9.2016.
 */
public interface AsyncVersionProvider {

    void getVersion(VersionProviderResult gResult);

    interface VersionProviderResult {

        void version(Version gVersion, String gPayload);

    }

}
