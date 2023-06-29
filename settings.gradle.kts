@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/deps.versions.toml"))
        }
    }
}
rootProject.name = "SuperApp"
include(":app")

include(":application:notes:app")
include(":application:notes:data")
include(":application:notes:domain")
include(":application:notes:presentation")

include(":core:common")
include(":core:database")
include(":core:di")
include(":core:navigation")
include(":core:network")
include(":core:ui")
