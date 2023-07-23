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

include(":application:convert")
include(":application:fitness")
include(":application:notes")

include(":core:common")
include(":core:database")
include(":core:datastore")
include(":core:di")
include(":core:navigation")
include(":core:network")
include(":core:ui")

include(":feature:currencies_convert")
include(":feature:notes")
include(":feature:tasks")
include(":feature:workout_program")

include(":application:fitness:app")
include(":application:fitness:data")
include(":application:fitness:domain")
include(":application:fitness:presentation")
