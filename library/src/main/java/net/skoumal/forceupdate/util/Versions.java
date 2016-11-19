package net.skoumal.forceupdate.util;

import android.util.ArraySet;

import net.skoumal.forceupdate.Version;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gingo on 18.11.2016.
 */
public class Versions {

    public static Set<String> toStringSet(List<Version> gVersionList) {
        Set<String> versionStrSet = new HashSet<>(gVersionList.size());

        for(Version v : gVersionList) {
            versionStrSet.add(v.toString());
        }

        return versionStrSet;
    }
}
