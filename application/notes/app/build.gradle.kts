plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.emikhalets.notes"
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

    implementation(project(":application:notes:data"))
    implementation(project(":application:notes:domain"))
    implementation(project(":application:notes:presentation"))
    implementation(project(":core:navigation"))

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

//    // Core
//    implementation 'androidx.core:core-ktx:1.7.0'
//    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
//    implementation "androidx.datastore:datastore-preferences:1.0.0"
//
//    // Compose
//    implementation 'androidx.activity:activity-compose:1.3.1'
//    implementation "androidx.compose.ui:ui:$compose_ui_version"
//    implementation "androidx.compose.ui:ui-tooling-preview:$compose_ui_version"
//    implementation 'androidx.compose.material:material:1.1.1'
//    implementation 'androidx.compose.material:material-icons-extended:1.3.1'
//    implementation "androidx.navigation:navigation-compose:2.5.3"
//    implementation "androidx.hilt:hilt-navigation-compose:1.0.0"
//
//    // Database
//    implementation "androidx.room:room-ktx:$room_version"
//    implementation "androidx.room:room-runtime:$room_version"
//    kapt "androidx.room:room-compiler:$room_version"
//
//    // DI
//    implementation "com.google.dagger:hilt-android:$hilt_version"
//    kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
//
//    // Testing
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
//    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_ui_version"
//    debugImplementation "androidx.compose.ui:ui-tooling:$compose_ui_version"
//    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_ui_version"
}