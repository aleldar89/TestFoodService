plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.catalog_data"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://anika1d.github.io/WorkTestServer\"")
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
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.bundles.room.bundle)
    ksp(libs.room.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.logging.interceptor)
}