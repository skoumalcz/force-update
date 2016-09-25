package net.skoumal.forceupdate;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ForceUpdate {

    private static final String SHARED_PREFERENCES_NAME = "ForceUpdate1234";

    private static final String SHARED_PREFERENCES_REQUEST_INTERRUPTED = "request_interrupted";

    private static final String SHARED_PREFERENCES_LAST_FORCE_VERSION_REQUEST = "last_force_version_request";

    private static final String SHARED_PREFERENCES_RECOMMENDED_VERSION_REQUEST = "last_recommended_version_request";

    private static boolean alreadyInstantiated;

    private Application application;

    private AsyncVersionProvider forcedVersionProvider;

    private int forcedVersionInterval;

    private AsyncVersionProvider recommendedVersionProvider;

    private int recommendedVersionInterval;

    private VersionProvider currentVersionProvider;

    private UpdateView forcedVersionView;

    private UpdateView recommendedVersionView;

    private SharedPreferences sharedPreferences;

    public ForceUpdate(Application gApplication,
                       AsyncVersionProvider gForcedVersionProvider, int gForcedVersionInterval,
                       AsyncVersionProvider gRecommendedVersionProvider, int gRecommendedVersionInterval,
                       VersionProvider gCurrentVersionProvider,
                       UpdateView gForcedVersionView,
                       UpdateView gRecommendedVersionView) {

        if(alreadyInstantiated) {
            throw new RuntimeException("ForceUpdate library is already initialized.");
        }

        application = gApplication;

        forcedVersionProvider = gForcedVersionProvider;

        forcedVersionInterval = gForcedVersionInterval;

        recommendedVersionProvider = gRecommendedVersionProvider;

        recommendedVersionInterval = gRecommendedVersionInterval;

        currentVersionProvider = gCurrentVersionProvider;

        forcedVersionView = gForcedVersionView;

        recommendedVersionView = gRecommendedVersionView;

        sharedPreferences = gApplication.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
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

    private void onActivityResumed(Activity activity) {
        checkForUpdate();

        //TODO [1] show force update view again if current version is still lower than forced version
    }

    private void checkForUpdate() {
        //TODO [1] finalize request interruption detection and show notification when last request was interrupted and there is update, ideal way is to show notification only in case of crash
        boolean previousRequestInterrupted = sharedPreferences.getBoolean(SHARED_PREFERENCES_REQUEST_INTERRUPTED, false);
        long lastForcedVersionRequest = sharedPreferences.getLong(SHARED_PREFERENCES_LAST_FORCE_VERSION_REQUEST, 0);
        long lastRecommendedVersionRequest = sharedPreferences.getLong(SHARED_PREFERENCES_RECOMMENDED_VERSION_REQUEST, 0);

        sharedPreferences.edit().putBoolean(SHARED_PREFERENCES_REQUEST_INTERRUPTED, true).apply();

        if(lastForcedVersionRequest + (forcedVersionInterval * 1000) < System.currentTimeMillis()) {
            forcedVersionProvider.getVersion(new AsyncVersionProvider.VersionProviderResult() {
                @Override
                public void version(Version gVersion, String gUpdateMessage) {
                    Version currentVersion = currentVersionProvider.getVersion();

                    if(currentVersion.compareTo(gVersion) < 0) {
                        forcedVersionView.showView(currentVersion, gVersion, gUpdateMessage);
                    }
                }
            });
        }

        if(lastRecommendedVersionRequest + (recommendedVersionInterval * 1000) < System.currentTimeMillis()) {
            recommendedVersionProvider.getVersion(new AsyncVersionProvider.VersionProviderResult() {
                @Override
                public void version(Version gVersion, String gUpdateMessage) {
                    Version currentVersion = currentVersionProvider.getVersion();

                    if(currentVersion.compareTo(gVersion) < 0) {
                        // TODO [1] avoid showing recommended view when force update is available
                        recommendedVersionView.showView(currentVersion, gVersion, gUpdateMessage);
                    }
                }
            });
        }

        if(previousRequestInterrupted) {
            // block main thread till the request finishes to avoid crash during request
        }
    }

    public static class Builder {

        private Application application;

        private AsyncVersionProvider forcedVersionProvider;

        private int forcedVersionInterval = 24 * 3600;

        private AsyncVersionProvider recommendedVersionProvider;

        private int recommendedVersionInterval = 24 * 3600;

        private VersionProvider currentVersionProvider;

        private UpdateView forcedVersionView;

        private UpdateView recommendedVersionView;

        public Builder() {

        }

        public Builder application(Application gApplication) {
            application = gApplication;

            return this;
        }

        public Builder forcedVersionProvider(AsyncVersionProvider gProvider) {
            forcedVersionProvider = gProvider;

            return this;
        }

        public Builder forcedVersionCheckMinInterval(int gSeconds) {
            forcedVersionInterval = gSeconds;

            return this;
        }

        public Builder recommendedVersionProvider(AsyncVersionProvider gProvider) {
            recommendedVersionProvider = gProvider;

            return this;
        }

        public Builder recommendedVersionCheckMinInterval(int gSeconds) {
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

        public ForceUpdate build() {
            return new ForceUpdate(application, forcedVersionProvider, forcedVersionInterval,
                    recommendedVersionProvider, recommendedVersionInterval, currentVersionProvider,
                    forcedVersionView, recommendedVersionView);
        }

        public ForceUpdate buildAndInit() {
            ForceUpdate fu = build();
            fu.init();

            return fu;
        }

    }
}
