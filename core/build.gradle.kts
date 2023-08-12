plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.emikhalets.core"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
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
        kotlinCompilerExtensionVersion = libs.versions.androidxCompose.get()
    }
}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.work.runtime)
    implementation(libs.google.hilt.android)
    implementation(libs.jsoup)

    kapt(libs.androidx.room.compiler)
    kapt(libs.google.hilt.compiler)

    api(libs.androidx.core)
    api(libs.androidx.navigation.compose)
    api(libs.jakewharton.timber)

    api(platform(libs.androidx.compose.bom))

    api(libs.bundles.androidx.compose)
    api(libs.bundles.androidx.coroutines)
    api(libs.bundles.androidx.lifecycle)
    api(libs.bundles.androidx.room)
    api(libs.bundles.google.accompanist)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}