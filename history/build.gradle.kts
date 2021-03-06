plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = 21
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        android.buildFeatures.viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.CORE_KTX}")
    implementation("androidx.appcompat:appcompat:${Versions.APP_COMPAT}")
    implementation("com.google.android.material:material:${Versions.MATERIAL}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}")
    testImplementation("junit:junit:${Versions.JUNIT}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.EXT_JUNIT}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES_CORE}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_ANDROID}")

    implementation("com.google.dagger:dagger-android:${Versions.DAGGER_ANDROID}")
    kapt("com.google.dagger:dagger-compiler:${Versions.DAGGER_COMPILER}")

    implementation(project(":core"))
    implementation(project(":room_api"))
}