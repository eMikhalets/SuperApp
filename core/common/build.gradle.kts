plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.emikhalets.core.common"
    compileSdk = extra["compileSdk"] as Int

    defaultConfig {
        minSdk = extra["minSdk"] as Int
    }

    compileOptions {
        sourceCompatibility = extra["java"] as JavaVersion
        targetCompatibility = extra["java"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = extra["java"].toString()
    }
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}