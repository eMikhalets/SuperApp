@file:Suppress("UnstableApiUsage")

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.emikhalets.medialib"
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

    implementation(project(":application:medialib:data"))
    implementation(project(":application:medialib:domain"))
    implementation(project(":application:medialib:presentation"))
    implementation(project(":core:navigation"))

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

//    // Compose
//    implementation platform('androidx.compose:compose-bom:2022.12.00')
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    implementation("androidx.compose.material:material")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.material:material-icons-core")
//    implementation("androidx.compose.material:material-icons-extended")
//    implementation("androidx.activity:activity-compose:1.6.1")
//    implementation("androidx.navigation:navigation-compose:2.5.3")
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//    implementation("com.google.accompanist:accompanist-pager:0.28.0")
//    implementation('com.google.accompanist:accompanist-pager-indicators:0.27.1')
//    implementation("io.coil-kt:coil-compose:2.2.2")
//
//    // Network
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//    // Database
//    implementation("androidx.room:room-runtime:$version_room")
//    implementation("androidx.room:room-ktx:$version_room")
//    kapt("androidx.room:room-compiler:$version_room")
//
//    // DI
//    implementation("com.google.dagger:hilt-android:$version_hilt")
//    kapt("com.google.dagger:hilt-compiler:$version_hilt")
//
//    // Local test
//    testImplementation("junit:junit:4.13.2")
//
//    // Instrumented test
//    androidTestImplementation("androidx.test.ext:junit:1.1.4")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    androidTestImplementation("androidx.room:room-testing:$version_room")
//
//    // Other
//    debugImplementation("androidx.compose.ui:ui-tooling:$version_compose")
//    debugImplementation 'androidx.compose.ui:ui-test-manifest'
}