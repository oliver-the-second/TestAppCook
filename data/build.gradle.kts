plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
}

baseConfig()

dependencies {
    androidBase(false)
    ktor()
    dataBase()
    kapt("androidx.room:room-compiler:2.4.3")
    implementation(project(":core"))
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
}