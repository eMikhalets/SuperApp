plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.emikhalets.core.database"
    compileSdk = extra["compileSdk"] as Int

    defaultConfig {
        minSdk = extra["minSdk"] as Int
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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

    implementation(project(":core:common"))

    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}