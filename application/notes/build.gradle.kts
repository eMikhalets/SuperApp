plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.emikhalets.notes"
    compileSdk = extra["compileSdk"] as Int

    defaultConfig {
        minSdk = extra["minSdk"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(":feature:notes"))
    implementation(project(":feature:tasks"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}