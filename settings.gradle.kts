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
include(":application:events")
include(":application:finance")
include(":application:fitness")
include(":application:medialib")
include(":application:notes")
include(":core")
