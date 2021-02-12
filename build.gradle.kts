plugins {
    id("com.android.library") version Versions.androidGradlePlugin apply false
    kotlin("android") version Versions.kotlin apply false
    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply false
}

apply(from = "dependencies.gradle")
apply(from = "modules.gradle")
