package net.skoumal.forceupdate;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gingo on 25.9.2016.
 */
public class Version implements Comparable<Version>, Parcelable {

    private int [] version;

    public Version(String gVersionString) {

        if(TextUtils.isEmpty(gVersionString) || !Character.isDigit(gVersionString.charAt(0))) {
            throw new RuntimeException("Accepted are only numbers with dots followed by anything. Examples of valid version strings are '0.1.2', '0.1.2-beta' or '0.1.2.3'.");
        }

        List<Integer> versionParts = new LinkedList<Integer>();

        String[] versionStringParts = gVersionString.split("\\.");

        for(String part : versionStringParts) {
            if(TextUtils.isEmpty(part)) {
                break;
            }

            if(TextUtils.isDigitsOnly(part)) {
                versionParts.add(Integer.valueOf(part));
            } else {
                int lastNumberIndex = -1;
                for(int i = 0; i < part.length(); i++) {
                    if(Character.isDigit(part.charAt(i))) {
                        lastNumberIndex = i;
                    } else {
                        break;
                    }
                }
                if(lastNumberIndex >= 0) {
                    versionParts.add(Integer.valueOf(part.substring(0, lastNumberIndex + 1)));
                }
                break;
            }
        }

        // convert list of Integers to array of priminive ints
        version = new int [versionParts.size()];
        for(int i = 0; i < versionParts.size(); i++) {
            version[i] = versionParts.get(i);
        }
    }

    public Version(int [] gVersionParts) {
        version = gVersionParts;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Version)) {
            return false;
        }

        Version comparedVersion = (Version)o;

        if(version.length != comparedVersion.version.length) {
            return false;
        }

        for (int i = 0; i < version.length; i++) {
            if(version[i] != comparedVersion.version[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(version);
    }

    @Override
    public int compareTo(Version another) {
        int minLength = Math.min(version.length, another.version.length);

        for(int i = 0; i < minLength; i++) {
            int version1 = version[i];
            int version2 = another.version[i];
            if(version1 != version2) {
                return version1 - version2;
            }
        }

        return 0;
    }

    public int [] getVersionParts() {
        return version;
    }

    protected Version(Parcel in) {
        in.readIntArray(version);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(version);
    }

    public static final Parcelable.Creator<Version> CREATOR = new Parcelable.Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel in) {
            return new Version(in);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };
}
