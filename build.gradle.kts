plugins {
    id("com.android.library") version Versions.androidGradlePlugin apply false
    kotlin("android") version Versions.kotlin apply false
    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply false
}

ext["deps2"] = mapOf(
    "jetbrainsAnnotations" to "20.0.0",
    "kotlin" to Versions.kotlin,
    "leakcanary2" to "2.4",
    "firebase" to mapOf(
        "crashlytics" to "17.0.0"
    )
)

apply(from = "dependencies.gradle")
apply(from = "modules.gradle")
