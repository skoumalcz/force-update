package net.skoumal.forceupdate.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by gingo on 23.10.2016.
 */
public class GooglePlay {

    public static void openAppDetail(Activity gContext) {
        String appPackage = gContext.getPackageName();
        Uri googlePlayUri = Uri.parse("market://details?id=" + appPackage);
        Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW, googlePlayUri);
        boolean googlePlayAvailable = false;

        // try to find official Google Play app
        final List<ResolveInfo> availableApps = gContext.getPackageManager().queryIntentActivities(googlePlayIntent, 0);
        for (ResolveInfo app: availableApps) {
            if (app.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo appActivity = app.activityInfo;
                ComponentName component = new ComponentName(
                        appActivity.applicationInfo.packageName,
                        appActivity.name
                );
                googlePlayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                googlePlayIntent.setComponent(component);
                gContext.startActivity(googlePlayIntent);
                googlePlayAvailable = true;
                break;

            }
        }

        if (!googlePlayAvailable) {
            if (availableApps.size() > 0) {
                // fallback to universal Google Play Intent
                gContext.startActivity(googlePlayIntent);
            } else {
                // if there is no app bind to market:// open Google Play in browser
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackage));
                gContext.startActivity(webIntent);
            }
        }
    }
}
