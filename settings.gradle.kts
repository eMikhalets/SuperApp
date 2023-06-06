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

include(":application:events:app")
include(":application:events:data")
include(":application:events:domain")
include(":application:events:presentation")

include(":application:finances:app")
include(":application:finances:data")
include(":application:finances:domain")
include(":application:finances:presentation")

include(":application:fitness:app")
include(":application:fitness:data")
include(":application:fitness:domain")
include(":application:fitness:presentation")

include(":application:medialib:app")
include(":application:medialib:data")
include(":application:medialib:domain")
include(":application:medialib:presentation")

include(":application:notes:app")
include(":application:notes:data")
include(":application:notes:domain")
include(":application:notes:presentation")

include(":core:common")
include(":core:database")
include(":core:navigation")
include(":core:ui")
