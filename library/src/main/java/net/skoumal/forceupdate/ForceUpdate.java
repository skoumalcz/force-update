package net.skoumal.forceupdate;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import net.skoumal.forceupdate.provider.ApkVersionProvider;
import net.skoumal.forceupdate.provider.CarretoVersionProvider;
import net.skoumal.forceupdate.view.activity.ForceUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class ForceUpdate {

    private static final String SHARED_PREFERENCES_NAME = "ForceUpdate1234";

    private static final String SHARED_PREFERENCES_REQUEST_INTERRUPTED = "request_interrupted";

    private static final String SHARED_PREFERENCES_LAST_MIN_ALLOWED_VERSION_REQUEST = "last_min_allowed_version_request";

    private static final String SHARED_PREFERENCES_LAST_RECOMMENDED_VERSION_REQUEST = "last_recommended_version_request";

    private static final String SHARED_PREFERENCES_LAST_EXCLUDED_VERSIONS_REQUEST = "last_excluded_versions_request";

    private static boolean alreadyInstantiated;

    private Application application;

    private AsyncVersionProvider forcedVersionProvider;

    private int forcedVersionInterval;

    private AsyncVersionProvider recommendedVersionProvider;

    private int recommendedVersionInterval;

    private AsyncVersionListProvider excludedVersionProvider;

    private int excludedVersionInterval;

    private VersionProvider currentVersionProvider;

    private UpdateView forcedVersionView;

    private UpdateView recommendedVersionView;

    private List<Class<?>> forceUpdateActivities;

    private SharedPreferences sharedPreferences;

    private Activity resumedActivity;

    public ForceUpdate(Application gApplication,
                       AsyncVersionProvider gForcedVersionProvider, int gForcedVersionInterval,
                       AsyncVersionProvider gRecommendedVersionProvider, int gRecommendedVersionInterval,
                       AsyncVersionListProvider gExcludedVersionListProvider, int gExcludedVersionListInterval,
                       VersionProvider gCurrentVersionProvider,
                       UpdateView gForcedVersionView,
                       UpdateView gRecommendedVersionView,
                       List<Class<?>> gForceUpdateActivities) {

        if(alreadyInstantiated) {
            throw new RuntimeException("ForceUpdate library is already initialized.");
        }

        String permission = "android.permission.INTERNET";
        int res = gApplication.checkCallingOrSelfPermission(permission);
        if(res != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Internet permission is necessary for version checks.");
        }

        if(gForcedVersionInterval < 60 || gRecommendedVersionInterval < 60 || gExcludedVersionListInterval < 60) {
            throw new RuntimeException("Minimal fetch interval is 60s");
        }

        application = gApplication;

        forcedVersionProvider = gForcedVersionProvider;

        forcedVersionInterval = gForcedVersionInterval;

        recommendedVersionProvider = gRecommendedVersionProvider;

        recommendedVersionInterval = gRecommendedVersionInterval;

        excludedVersionProvider  = gExcludedVersionListProvider;

        excludedVersionInterval = gExcludedVersionListInterval;

        currentVersionProvider = gCurrentVersionProvider;

        forcedVersionView = gForcedVersionView;

        recommendedVersionView = gRecommendedVersionView;

        forceUpdateActivities = gForceUpdateActivities;

        sharedPreferences = gApplication.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        alreadyInstantiated = true;
    }

    public void init() {
        checkForUpdate();

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                ForceUpdate.this.onActivityResumed(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                ForceUpdate.this.onActivityPaused(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void onActivityResumed(Activity gActivity) {

        boolean isForceUpdateActivity = false;

        //TODO [1] detect infinite loop because of showView -> onActivityResumed loop

        for(Class<?> c : forceUpdateActivities) {
            if(c.isInstance(gActivity)) {
                isForceUpdateActivity = true;
                break;
            }
        }

        if(isForceUpdateActivity) {
            resumedActivity = gActivity;

            checkForUpdate();

            //TODO [1] show force update view again if current version is still lower than forced version
        }
    }

    private void onActivityPaused(Activity activity) {
        resumedActivity = null;
    }

    private void checkForUpdate() {
        //TODO [1] finalize request interruption detection and show notification when last request was interrupted and there is update, ideal way is to show notification only in case of crash
        boolean previousRequestInterrupted = sharedPreferences.getBoolean(SHARED_PREFERENCES_REQUEST_INTERRUPTED, false);
        long lastMinAllowedVersionRequest = sharedPreferences.getLong(SHARED_PREFERENCES_LAST_MIN_ALLOWED_VERSION_REQUEST, 0);
        long lastRecommendedVersionRequest = sharedPreferences.getLong(SHARED_PREFERENCES_LAST_RECOMMENDED_VERSION_REQUEST, 0);
        long lastExcludedVersionRequest = sharedPreferences.getLong(SHARED_PREFERENCES_LAST_EXCLUDED_VERSIONS_REQUEST, 0);

        sharedPreferences.edit().putBoolean(SHARED_PREFERENCES_REQUEST_INTERRUPTED, true).apply();

        if(forcedVersionProvider != null &&
                lastMinAllowedVersionRequest + (forcedVersionInterval * 1000) < System.currentTimeMillis()) {
            forcedVersionProvider.getVersion(new AsyncVersionProvider.VersionProviderResult() {
                @Override
                public void version(Version gVersion, String gPayload) {
                    Version currentVersion = currentVersionProvider.getVersion();

                    if(currentVersion.compareTo(gVersion) < 0 && resumedActivity != null) {
                        forcedVersionView.showView(resumedActivity, currentVersion, gVersion, gPayload);
                    }
                }

                @Override
                public void error(String gMessage) {
                    ForceUpdateLogger.e(gMessage);
                }
            });
        }

        if(recommendedVersionProvider != null &&
                lastRecommendedVersionRequest + (recommendedVersionInterval * 1000) < System.currentTimeMillis()) {
            recommendedVersionProvider.getVersion(new AsyncVersionProvider.VersionProviderResult() {
                @Override
                public void version(Version gVersion, String gPayload) {
                    Version currentVersion = currentVersionProvider.getVersion();

                    if(currentVersion.compareTo(gVersion) < 0 && resumedActivity != null) {
                        // TODO [1] avoid showing recommended view when force update is available
                        recommendedVersionView.showView(resumedActivity, currentVersion, gVersion, gPayload);
                    }
                }

                @Override
                public void error(String gMessage) {
                    ForceUpdateLogger.e(gMessage);
                }
            });
        }

        if(excludedVersionProvider != null &&
                lastExcludedVersionRequest + (excludedVersionInterval * 1000) < System.currentTimeMillis()) {
            excludedVersionProvider.getVersionList(new AsyncVersionListProvider.VersionListProviderResult() {
                @Override
                public void versionList(List<Version> gVersionList, List<String> gPayloadList) {
                    Version currentVersion = currentVersionProvider.getVersion();

                    for(int i = 0; i < gVersionList.size(); i++) {
                        Version version = gVersionList.get(i);
                        if (currentVersion.compareTo(version) < 0 && resumedActivity != null) {
                            // TODO [1] avoid showing another forced update when it is already visible
                            forcedVersionView.showView(resumedActivity, currentVersion, version, gPayloadList.get(i));
                        }
                    }
                }

                @Override
                public void error(String gMessage) {
                    ForceUpdateLogger.e(gMessage);
                }
            });
        }

        if(previousRequestInterrupted) {
            // TODO [2] block main thread till the request finishes to avoid crash during request
        }
    }

    public static class Builder {

        private Application application;

        private AsyncVersionProvider minAllowedVersionProvider = null;

        private int minAllowedVersionInterval = 24 * 3600;

        private AsyncVersionProvider recommendedVersionProvider;

        private int recommendedVersionInterval = 24 * 3600;

        private AsyncVersionListProvider excludedVersionListProvider;

        private int excludedVersionListInterval = 24 * 3600;

        private VersionProvider currentVersionProvider;

        //TODO [1] define default activity view
        private UpdateView forcedVersionView;

        //TODO [1] define default dialog view
        private UpdateView recommendedVersionView;

        private List<Class<?>> forceUpdateActivities = new ArrayList<>();

        public Builder() {

        }

        public Builder application(Application gApplication) {
            application = gApplication;

            forceUpdateActivities.add(ForceUpdateActivity.class);

            return this;
        }

        public Builder minAllowedVersionProvider(AsyncVersionProvider gProvider) {
            minAllowedVersionProvider = gProvider;

            return this;
        }

        public Builder minAllowedVersionCheckMinInterval(int gSeconds) {
            if(gSeconds < 60) {
                throw new RuntimeException("Minimum check interval is 60s");
            }

            minAllowedVersionInterval = gSeconds;

            return this;
        }

        public Builder excludedVersionListProvider(AsyncVersionListProvider gProvider) {
            excludedVersionListProvider = gProvider;

            return this;
        }

        public Builder excludedVersionListCheckMinInterval(int gSeconds) {
            if(gSeconds < 60) {
                throw new RuntimeException("Minimum check interval is 60s");
            }

            excludedVersionListInterval = gSeconds;

            return this;
        }

        public Builder recommendedVersionProvider(AsyncVersionProvider gProvider) {
            recommendedVersionProvider = gProvider;

            return this;
        }

        public Builder recommendedVersionCheckMinInterval(int gSeconds) {
            if(gSeconds < 60) {
                throw new RuntimeException("Minimum check interval is 60s");
            }

            recommendedVersionInterval = gSeconds;

            return this;
        }

        public Builder currentVersionProvider(VersionProvider gProvider) {
            currentVersionProvider = gProvider;

            return this;
        }


        public Builder forcedUpdateView(UpdateView gUpdateView) {
            forcedVersionView = gUpdateView;

            return this;
        }

        public Builder recommendedUpdateView(UpdateView gUpdateView) {
            recommendedVersionView = gUpdateView;

            return this;
        }

        public Builder addForceUpdateActivity(Class<?> gActivity) {
            forceUpdateActivities.add(gActivity);

            return this;
        }

        public ForceUpdate build() {
            if(application == null) {
                throw new RuntimeException("Please call ForceUpdate.Builder#application(Application) method before build.");
            }

            if(currentVersionProvider == null) {
                currentVersionProvider = new ApkVersionProvider(application);
            }

            if(recommendedVersionProvider == null) {
                recommendedVersionProvider = new CarretoVersionProvider(application);
            }

            return new ForceUpdate(application,
                    minAllowedVersionProvider, minAllowedVersionInterval,
                    recommendedVersionProvider, recommendedVersionInterval,
                    excludedVersionListProvider, excludedVersionListInterval,
                    currentVersionProvider,
                    forcedVersionView, recommendedVersionView,
                    forceUpdateActivities);
        }

        public ForceUpdate buildAndInit() {
            ForceUpdate fu = build();
            fu.init();

            return fu;
        }

    }
}
