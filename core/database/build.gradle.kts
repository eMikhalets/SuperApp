plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
}

android {
    namespace = "com.emikhalets.database"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
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
}

dependencies {

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
}