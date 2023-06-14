plugins {
    id("com.android.library")
    kotlin("android")
}

baseConfig()

dependencies {
    androidBase(false)
    implementation(project(":domain"))
    implementation(project(":core"))

}