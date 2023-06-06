plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.emikhalets.medialib.presentation"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
    }

    compileOptions {
        sourceCompatibility = rootProject.extra["java"] as JavaVersion
        targetCompatibility = rootProject.extra["java"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["java"].toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(project(":application:medialib:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}