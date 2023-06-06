@file:Suppress("UnstableApiUsage")

import java.io.FileInputStream
import java.util.Properties

val keystorePropertiesFile: File? = rootProject.file("keystore.properties")
val keystoreProperties: Properties = Properties()
keystorePropertiesFile?.let { keystoreProperties.load(FileInputStream(it)) }

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.emikhalets.superapp"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.emikhalets.superapp"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPass"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePass"] as String
        }
//        create("release") {
//            keyAlias = "release"
//            keyPassword = "my release key password"
//            storeFile = file("/home/miles/keystore.jks")
//            storePassword = "my keystore password"
//        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        val release by getting {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

//    implementation(project(":application:events:app"))
//    implementation(project(":application:finances:app"))
//    implementation(project(":application:fitness:app"))
//    implementation(project(":application:medialib:app"))
//    implementation(project(":application:notes:app"))

    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.google.accompanist)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}