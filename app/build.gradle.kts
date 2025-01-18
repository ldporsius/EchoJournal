plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "nl.codingwithlinda.echojournal"
    compileSdk = 35

    defaultConfig {
        applicationId = "nl.codingwithlinda.echojournal"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //fonts
    implementation(libs.androidx.ui.text.google.fonts)

    //splash
    implementation (libs.androidx.core.splashscreen)

    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.rules)

    //desugar
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    testImplementation(libs.junit.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)

    // Instrumentation tests
    androidTestImplementation (libs.coroutines.test)
    androidTestImplementation (libs.androidx.core.testing)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.androidx.junit.v113)
    androidTestImplementation (libs.core.ktx)
    androidTestImplementation (libs.androidx.compose.ui.ui.test.junit4)
    //androidTestImplementation(platform(libs.androidx.compose.bom)) outcommented because don't understand
    androidTestImplementation (libs.androidx.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.orchestrator)
    androidTestImplementation(libs.androidx.test.uiautomator)
    androidTestImplementation (libs.androidx.runner)


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}