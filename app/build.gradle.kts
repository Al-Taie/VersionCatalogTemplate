import com.gateway.buildscr.Config
import com.gateway.buildscr.Config.Version
import com.gateway.buildscr.getLocalProperty

private object LocalConfig {
    const val CODE = 1
    const val NAME = "0.0.${CODE - 1}"
}

private object ProductionConfig {
    const val CODE = 1
    const val NAME = "1.0.0-alpha0$CODE"
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = Config.APPLICATION_ID
    compileSdk = Version.COMPILE_SDK
    buildToolsVersion = Version.BUILD_TOOLS

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = Version.MIN_SDK
        targetSdk = Version.TARGET_SDK
        versionCode = LocalConfig.CODE
        versionName = LocalConfig.NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    val baseUrl = "BASE_URL"
    val baseUrlDev = "BASE_URL_DEV"
    val baseUrlProd = "BASE_URL_PROD"

    flavorDimensions.add("mode")

    productFlavors {
        create("development") {
            dimension = "mode"
            applicationIdSuffix = ".dev"
            buildConfigField("String", baseUrl, getLocalProperty(key = baseUrlDev))
        }

        create("production") {
            dimension = "mode"
            buildConfigField("String", baseUrl, getLocalProperty(key = baseUrlProd))
            versionCode = ProductionConfig.CODE
            versionName = ProductionConfig.NAME
        }
    }

    compileOptions {
        sourceCompatibility = Version.JVM
        targetCompatibility = Version.JVM
    }

    kotlinOptions {
        jvmTarget = Version.JVM.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.COMPOSE_COMPILER
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        named("development") {
            res {
                srcDirs("src/development/res")
            }
        }
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

kotlin {
    jvmToolchain(Version.JVM.majorVersion.toInt())
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.ktx.viewmodel)
    implementation(libs.androidx.lifecycle.ktx.runtime)
    implementation(libs.compose.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.fonts)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.lifecycle.viewmodel)
    implementation(libs.compose.navigation)
    implementation(libs.compose.navigation.hilt)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.utils.timber)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.test.junit4)
    debugImplementation(libs.compose.test.manifest)
    debugImplementation(libs.compose.ui.tooling)
}
