
plugins {
    id("com.android.library")
    kotlin("android")
}

baseConfig()

dependencies {
    androidBase(false)
    ktor()
    dataStore()
    implementation(project(":data"))
    implementation(project(":core"))
}