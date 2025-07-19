import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
//    alias(libs.plugins.googleGmsGoogleServices)

}

kotlin {

    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }

//    jvm()

//    iosX64() // this is the one i need

//    iosArm64()
//    iosSimulatorArm64()

//    cocoapods {
//        summary = "Some description for the Shared Module"
//        homepage = "Link to the Shared Module homepage"
//        version = "1.0"
//        ios.deploymentTarget = "16.0"
//        podfile = project.file("../iosApp/Podfile")
//        framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }
    
    sourceSets {

//        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)
//         //   implementation(libs.koin.core)
//
//        }


        androidMain.dependencies {
            implementation(compose.preview)
//            implementation(compose.components.resources)
            implementation(libs.koin.android)
//            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.activity.compose)
            implementation(libs.firebase.messaging)

        }

        commonMain.dependencies {
            //put your multiplatform dependencies here
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.material)
//            implementation(compose.ui)
//            implementation(compose.components.resources)
//            implementation(compose.components.uiToolingPreview)
//            implementation(libs.androidx.lifecycle.viewmodel)
//            implementation(libs.androidx.lifecycle.runtime.compose)
//            implementation(libs.multiplatform.settings)
//            implementation(libs.koin.core)
//            implementation(libs.ktor.client.websockets)
//            implementation(libs.koin.compose)
//            implementation(libs.bundles.ktor)
//            implementation(libs.image.loader)
//            implementation(libs.voyager.navigator)
//            implementation(libs.voyager.transitions)
//            implementation(libs.kmp.date.time.picker)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
//            implementation(compose.tooling.preview)
            implementation(libs.multiplatform.settings)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.image.loader)
            implementation(libs.bundles.ktor)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.kmp.date.time.picker)

            implementation(libs.hotpreview)

        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "ghayatech.ihubs"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}