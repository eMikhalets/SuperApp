plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kapt)
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.hilt) apply false
}
tasks.register("clean").configure {
    delete(rootProject.buildDir)
}