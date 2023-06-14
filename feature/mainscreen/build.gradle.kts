plugins {
    id("com.android.library")
    kotlin("android")
}

baseConfig()
viewBinding(true)

dependencies {
    androidBase(false)
    implementation(project(":domain"))
    implementation(project(":core"))


}