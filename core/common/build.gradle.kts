plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.emikhalets.superapp.core.common"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    }
    kotlinOptions {
        jvmTarget = JavaVersion.valueOf(libs.versions.java.get()).toString()
    }
}

dependencies {
    api(libs.core)
    api(libs.coroutines.core)
    api(libs.coroutines.android)
    api(libs.lifecycle.runtime.compose)
    api(libs.lifecycle.viewmodel)
    api(libs.lifecycle.viewmodel.compose)
    api(libs.accompanist.insets)
    api(libs.accompanist.systemuicontroller)
    api(libs.timber)
    api(libs.kotlin.serialization.json)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
}
