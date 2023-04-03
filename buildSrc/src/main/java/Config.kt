package com.gateway.buildscr

import org.gradle.api.JavaVersion

object Config {
    object Version {
        const val MIN_SDK = 24
        const val TARGET_SDK = 33
        const val COMPILE_SDK = 33
        val JVM = JavaVersion.VERSION_1_8
        const val BUILD_TOOLS = "33.0.1"
        const val COMPOSE_COMPILER = "1.4.2"
    }

    const val APPLICATION_ID = "com.altaie.versionCatalogTemplate"
    const val ANDROID_TEST_INSTRUMENTATION = "androidx.test.runner.AndroidJUnitRunner"
}
