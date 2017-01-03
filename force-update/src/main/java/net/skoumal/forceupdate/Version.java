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
            throw new RuntimeException("Invalid version format \"" + gVersionString + "\". Accepted are only numbers with dots followed by anything. Examples of valid version strings are '0.1.2', '0.1.2-beta' or '0.1.2.3'.");
        }

        List<Integer> versionParts = new LinkedList<Integer>();

        String[] versionStringParts = gVersionString.split("\\.");

        for(String part : versionStringParts) {
            if(TextUtils.isEmpty(part)) {
                break;
            }

            if(isDigit(part)) {
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
        int [] versionPartsArray = new int [versionParts.size()];
        for(int i = 0; i < versionParts.size(); i++) {
            versionPartsArray[i] = versionParts.get(i);
        }

        init(versionPartsArray);
    }

    public Version(int [] gVersionParts) {
        init(gVersionParts.clone());
    }

    private void init(int[] gVersionParts) {
        for(int part : gVersionParts) {
            if(part < 0) {
                throw new RuntimeException("Only positive numbers are allowed, you provided " + part + " as one part of version.");
            }
        }

        version = gVersionParts;
    }

    private static boolean isDigit(CharSequence str) {
        final int len = str.length();

        if(len > 0) {
            char firstChar = str.charAt(0);
            if(!Character.isDigit(firstChar) && firstChar != '-') {
                return false;
            }
        }

        for (int i = 1; i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
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

    @Override
    public String toString() {
        String versionName = "";
        for (int i = 0; i < version.length; i++) {
            versionName += Integer.toString(version[i]);
            if (i + 1 < version.length) {
                versionName += ".";
            }
        }
        return versionName;
    }

    public int [] getVersionParts() {
        return version.clone();
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
