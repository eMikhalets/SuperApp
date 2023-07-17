@file:Suppress("COMPATIBILITY_WARNING")

ext {
    extra["compileSdk"] = 33
    extra["targetSdk"] = 33
    extra["minSdk"] = 26
    extra["versionCode"] = 1
    extra["versionName"] = "1.0.1"
    extra["java"] = JavaVersion.VERSION_17
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
}
tasks.register("clean").configure {
    delete(rootProject.buildDir)
}