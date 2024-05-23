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
include(":application:salary")
include(":core:common")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:ui")
include(":data:salary")
include(":domain:salary")
