pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TestAppCook"
include (":app")
include(":data")
include(":domain")
include(":feature:mainscreen")
include(":feature:basket")
include(":feature:search")
include(":feature:account")
include(":core")
