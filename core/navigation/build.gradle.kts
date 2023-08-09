plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.emikhalets.core.navigation"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    api(libs.androidx.navigation.compose)
    api(libs.androidx.hilt.compose)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}