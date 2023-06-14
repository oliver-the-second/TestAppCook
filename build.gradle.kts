
buildscript{
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        google()
    }
    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
    }
}