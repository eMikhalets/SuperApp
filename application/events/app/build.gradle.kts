@file:Suppress("UnstableApiUsage")

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.emikhalets.events"
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
//    // Core
//    implementation("androidx.core:core-ktx:1.8.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
//    implementation("androidx.work:work-runtime-ktx:2.7.1")
//    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1'
////    implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")
//
//    // Compose
//    implementation platform('androidx.compose:compose-bom:2022.12.00')
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material:material")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.material:material-icons-core")
//    implementation("androidx.compose.material:material-icons-extended")
//    implementation("androidx.activity:activity-compose:1.6.1")
//    implementation("androidx.navigation:navigation-compose:2.5.3")
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//
//    // Network
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")
//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
//
//    // Third-party
//    implementation('io.coil-kt:coil-compose:2.2.2')
//    implementation("com.jakewharton.timber:timber:5.0.1")
//
//    // Local test
//    testImplementation("junit:junit:4.13.2")
//
//    // Instrumented test
//    androidTestImplementation("androidx.test.ext:junit:1.1.4")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${versions.compose}")
//    androidTestImplementation("androidx.room:room-testing:$room_version")
//
//    // Other
//    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")

    implementation(project(":application:events:data"))
    implementation(project(":application:events:domain"))
    implementation(project(":application:events:presentation"))
    implementation(project(":core:navigation"))

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}