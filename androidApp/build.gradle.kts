plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.googleGmsGoogleServices)

}

android {
    namespace = "ghayatech.ihubs.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "ghayatech.ihubs.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
//    implementation(projects.shared)
//    implementation(libs.compose.ui)
//    implementation(libs.compose.ui.tooling.preview)
//    implementation(libs.compose.material)
//    implementation(libs.androidx.activity.compose)
//    implementation(libs.koin.android)
//    implementation(libs.ktor.client.okhttp)
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.androidx.activity.compose)
//    implementation(libs.compose.tooling.preview)
    implementation(libs.koin.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.image.loader)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
//    implementation(libs.firebase.messaging)
    implementation(libs.firebase.messaging)
//    implementation(libs.ktor.client.websockets)
}