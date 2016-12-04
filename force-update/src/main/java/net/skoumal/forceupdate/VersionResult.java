package net.skoumal.forceupdate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gingo on 7.10.2016.
 */
public class VersionResult {

    private List<Version> versionList = new ArrayList<>(1);

    private List<String> payloadList = new ArrayList<>(1);

    private boolean isError = false;

    private String errorMessage = null;

    private Exception errorException = null;

    public VersionResult(Version gVersion, String gPayload) {
        versionList.add(gVersion);
        payloadList.add(gPayload);
    }

    public VersionResult(String gErrorMessage, Exception gErrorException) {
        errorMessage = gErrorMessage;
        errorException = gErrorException;
        isError = true;
    }

    public VersionResult(List<Version> gVersionList, List<String> gPayloadList) {
        versionList = gVersionList;
        payloadList = gPayloadList;
    }

    public Version getVersion() {
        if(isError || versionList.size() < 1) {
            return null;
        } else {
            return versionList.get(0);
        }
    }

    public String getPayload() {
        if(isError || payloadList.size() < 1) {
            return null;
        } else {
            return payloadList.get(0);
        }
    }

    public List<Version> getVersionList() {
        return versionList;
    }

    public List<String> getPayloadList() {
        return payloadList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Exception getErrorException() {
        return errorException;
    }

    public boolean isError() {
        return isError;
    }
}
