package net.skoumal.forceupdate;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gingo on 25.9.2016.
 */
public class Version implements Comparable<Version> {

    private int [] version;

    public Version(String gVersionString) {

        if(TextUtils.isEmpty(gVersionString) || !Character.isDigit(gVersionString.charAt(0))) {
            throw new RuntimeException("Accepted are only numbers with dots followed by anything. Examples of valid version strings are '0.1.2', '0.1.2-beta' or '0.1.2.3'.");
        }

        List<Integer> versionParts = new LinkedList<Integer>();

        StringBuilder currentPart = new StringBuilder();
        for(int i = 0; true; i++) {

            char currentChar = gVersionString.charAt(i);
            boolean currentCharIsDigit = Character.isDigit(currentChar);
            boolean currentCharIsDot = currentChar == '.';
            boolean currentCharIsLastOne = i == gVersionString.length() - 1;

            if(currentCharIsDigit) { // new digit in part
                currentPart.append(gVersionString.charAt(i));
            }


            if(!currentCharIsDigit || i == gVersionString.length() - 1) { // end of part
                versionParts.add(Integer.valueOf(currentPart.toString()));
                currentPart = new StringBuilder();
            }

            if((!currentCharIsDigit && !currentCharIsDot) || currentCharIsLastOne) {
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
}
