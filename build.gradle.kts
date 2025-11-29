plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false

    // CHANGE: Add the version number here.
    // Version 4.4.2 is a common stable version, but you can check for newer ones.
    id("com.google.gms.google-services") version "4.4.2" apply false
}
