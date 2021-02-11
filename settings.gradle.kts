pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.namespace) {
                "com.android" -> useModule("com.android.tools.build:gradle:${extra["gradlePluginAndroidVersion"]}")
                "org.jetbrains.kotlin" -> useVersion("${extra["kotlinVersion"]}")
            }
        }
    }
}

include("gto-support-androidx-collection")
include("gto-support-androidx-databinding")
include("gto-support-androidx-fragment")
include("gto-support-androidx-lifecycle")
include("gto-support-androidx-room")
include("gto-support-androidx-viewpager2")
include("gto-support-androidx-work")
include("gto-support-api-base")
include("gto-support-api-okhttp3")
include("gto-support-api-retrofit2")
include("gto-support-appcompat")
include("gto-support-base")
include("gto-support-compat")
include("gto-support-core")
include("gto-support-dagger")
include("gto-support-db")
include("gto-support-db-async")
include("gto-support-db-coroutines")
include("gto-support-db-livedata")
include("gto-support-db-stream")
include("gto-support-eventbus")
include("gto-support-facebook-flipper")
include("gto-support-firebase-crashlytics")
include("gto-support-jsonapi")
include("gto-support-jsonapi-retrofit2")
include("gto-support-jsonapi-scarlet")
include("gto-support-kotlin-coroutines")
include("gto-support-leakcanary2")
include("gto-support-lottie")
include("gto-support-material-components")
include("gto-support-moshi")
include("gto-support-okhttp3")
include("gto-support-okta")
include("gto-support-picasso")
include("gto-support-realm")
include("gto-support-recyclerview")
include("gto-support-recyclerview-advrecyclerview")
include("gto-support-scarlet")
include("gto-support-scarlet-actioncable")
include("gto-support-snowplow")
include("gto-support-sync")
include("gto-support-util")
include("gto-support-viewpager")

include("testing:gto-support-picasso")
include("testing:gto-support-timber")

// deprecated modules
include("gto-support-databinding")
include("gto-support-design")
include("gto-support-lifecycle")
include("gto-support-room")
include("gto-support-testing")
