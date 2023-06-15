
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}

plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    kotlin("android")
    id("kotlin-kapt")

}

baseConfig()

compose(false)

viewBinding(true)

android {
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.6")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    androidBase(false)
    dataBase()
    kapt("androidx.room:room-compiler:2.4.3")
    //compose()

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:basket"))
    implementation(project(":feature:mainscreen"))
    implementation(project(":feature:search"))
    implementation(project(":feature:account"))
    implementation(project(":shared"))


}