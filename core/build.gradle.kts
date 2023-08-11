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
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.navigation.compose)
    api(libs.google.accompanist.systemUi)
    api(libs.google.accompanist.insets)

    implementation(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material)
    api(libs.androidx.compose.material.icons)
    api(libs.androidx.compose.material.icons.ext)


    implementation(libs.jsoup)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)
}