package net.skoumal.forceupdate.util;

import android.util.ArraySet;

import net.skoumal.forceupdate.Version;

import java.util.ArrayList;
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

    public static List<Version> toVersionList(Set<String> gStringSet) {
        ArrayList<Version> versionList = new ArrayList<>(gStringSet.size());
        for (String s : gStringSet) {
            versionList.add(new Version(s));
        }

        return versionList;
    }

    public static void decrementVersion(int[] apkVersionParts) {
        for(int i = apkVersionParts.length - 1; i >= 0; i--) {
            if(apkVersionParts[i] > 0) {
                apkVersionParts[i] --;
                break;
            } else {
                apkVersionParts[i] = 9;
            }
        }
    }

    public static void incrementVersion(int[] versionParts) {
        for(int i = versionParts.length - 1; i >= 0; i--) {
            if(versionParts[i] < 9) {
                versionParts[i] ++;
                break;
            } else {
                versionParts[i] = 0;
            }
        }
    }
}
