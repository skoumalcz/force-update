ForceUpdate
============

This library is currently in __concept phase__ feel free to give it a star, but do not try to use it :-)

Force your users to update the app, or notify about non-critical updates.


Download
--------

Download the latest JAR:

[ ![Download](https://api.bintray.com/packages/skoumal/maven/force-update/images/download.svg) ](https://bintray.com/skoumal/maven/force-update/_latestVersion)

or via Gradle:

```groovy
compile 'net.skoumal.forceupdate:force-update:0.1.0'
```

Recommended usage
-----------------

Only few lines of code are needed to notify your users about new updates and force them to update
in edge cases, like critical error in app or backend incompatibility with old version of your app.

Let's publish somewhere JSON in this format:

```json
    {
        "min_allowed_version": "1.0.0",
        "excluded_versions" : [ { "version" : "1.0.3" }, { "version" : "2.0.1" }]
    }
```

Now add ForceUpdate initialization in your Application.onCreate():

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // load all data from our json file
        .masterVersionProvider(new JsonHttpMasterVersionProvider("http://your.domain/path/to/above.json"))

        // with exception for new version notifications
        .recommendedVersionProvider(new CarretoGooglePlayVersionProvider())

        // we are done
        .buildAndInit();
```

Now all your users will be:

- forced to update all installations with lower version than 1.0.0
- forced to update installations with versions 1.0.3 and 2.0.1
- notified about available update with every new version on Google Play

Version providers
-----------------

You can load your version requirements from any place and any format. There is set of [ready-to-use
version providers](blob/master/doc/VersionProviders.md) and you can write your own if you need
something special.

By default every provider is asked once per 24 hours for its version.

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // custom provider for minimal required version
        .minAllowedVersionProvider(new MyCustomVersionProvider())

        // ask min-allowed-version provider every 4 hours for version
        .minAllowedVersionCheckMinInterval(4 * 3600)

        // custom provider for recommended version provider
        .recommendedVersionProvider(new AnotherCustomVersionProvider())

        // ask recommended-version provider every 6 hours for version
        .recommendedVersionCheckMinInterval(6 * 3600)

        // custom provider of exluded version list
        .excludedVersionProvider(new MyExcludedVersionListProvider())

        // ask excluded-version provider every 8 hours
        .excludedVersionCheckMinInterval(8 * 3600)

        // you can also load apk version your own way if needed
        .currentVersionProvider(new ApkVersionProvider())

        .buildAndInit();
```

Sometimes you need to load more data from one physycal source, like in our first example, where we
load min-allowed and excluded versions from one JSON file:

```json
    {
        "min_allowed_version": "1.0.0",
        "excluded_versions" : [ { "version" : "1.0.3" }, { "version" : "2.0.1" }],
        "recommended_version" : "3.0.3"
    }
```

You can load all three versions from JSON file above

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // one provider for all versions, could be overwritten by methods above
        .masterVersionProvider(new JsonHttpMasterVersionProvider("http://your.domain/path/to/above.json"));

        .buildAndInit();
```


View customization
------------------

Simply define your own views for force-update Activity and/or for recommended-update Dialog:

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // defaults to predefined activity
        .forcedUpdateView(R.layout.force_update_activity)

        // defaults to predefined dialog
        .recommendedUpdateView(R.layout.recommend_update_dialog)

        .buildAndInit();
```

Use your own activities:

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // defaults to predefined activity
        .forcedUpdateView(MyForceUpdateActivity.class)

        // defaults to predefined dialog
        .recommendedUpdateView(MyRecommendedUpdateActivity.class)

        .buildAndInit();
```

Or provide completely custom implementation of UpdateView interface:

Use your own activities:

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // defaults to predefined activity
        .forcedUpdateView(new MyUpdateView())

        // defaults to predefined dialog
        .recommendedUpdateView(new OtherUpdateView())

        .buildAndInit();
```


Usage
-----

This library was designed with full customisability in mind. Whole library is based on three simple
terms:

* **VersionProvider** - provides version from some source, for example from your APK, Google Play,
your server, or any other third party service. You can choose one of existing VersionProviders, or
create your own.

* **UpdateView** - abstraction of the way how we notify user about new version available
(recommended update) or about necessary update (forced update). Predefined Activities and dialogs
available again.

* **CheckInterval** - how often should we ask _VersionProvider_ for new data. The under-the-hood
strategy is to ask with every _Activity.onResume()_ event if _MinInterval_ is already reached.

To init ForceUpdate library use this simple builder in your Application:

```java
    new ForceUpdate.Builder()

        // required - needs android.app.Application object
        .application(this)

        // defaults to NULL - no force update view will ever appear
        .minAllowedVersionProvider(new MyCustomVersionProvider())

        // defaults to 24 hours
        .minAllowedVersionCheckMinInterval(4 * 3600)

        // defaults to version from http://carreto.pt/tools/android-store-version/?package=<your package>
        .recommendedVersionProvider(new CarretoGooglePlayVersionProvider())

        // defaults to 24 hours
        .recommendedVersionCheckMinInterval(6 * 3600)

        // defaults to NULL - no force update view will ever appear
        .excludedVersionProvider(new MyExcludedVersionListProvider())

        // defaults to 24 hours
        .excludedVersionCheckMinInterval(8 * 3600)

        // defaults to current apk version
        .currentVersionProvider(new ApkVersionProvider())

        // defaults to predefined activity
        .forcedUpdateView(new ForcedVersionActivityView())

        // defaults to predefined dialog
        .recommendedUpdateView(new RecommendedVersionView())

        // one provider for all versions, overrides all VersionProviders
        .masterVersionProvider(new MyMasterProvider());

        // alias for .build().init()
        .buildAndInit();
```

Extras
------

Library is able to detect crash during last version check and hangs main thread during next app
start to avoid any other code execution and allow successful version check. If there is new version
available it fires notification automatically.

Contact
=======

Feel free to [report any issues](https://github.com/skoumalcz/force-update/issues/new) or for the library on contribute few pieces of code!

Follow us on Twitter! [@skoumal_dev](https://twitter.com/skoumal_dev)

License
=======

    Copyright 2016 SKOUMAL, s.r.o.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
