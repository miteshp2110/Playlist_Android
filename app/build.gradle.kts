import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
    namespace = "com.xceptions.playlist"
    compileSdk = 35

    defaultConfig {

        // Load properties from the local.properties file
        val localProperties = Properties().apply {
            try {
                rootProject.file("local.properties").inputStream().use { load(it) }
            } catch (e: Exception) {
                println("Warning: local.properties file not found or could not be read.")
            }
        }
        val googleClientId = localProperties.getProperty("GOOGLE_CLIENT_ID") ?: "default_client_id"
        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$googleClientId\"")

        val tokenKey = localProperties.getProperty("TOKEN_KEY") ?: "tokenKey"
        buildConfigField("String", "TOKEN_KEY", "\"$tokenKey\"")

        val BASE_URL = localProperties.getProperty("BASE_URL") ?: "/"
        buildConfigField("String","BASE_URL","\"$BASE_URL\"")

        applicationId = "com.xceptions.playlist"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.squareup.retrofit2:retrofit:+")
    implementation("com.squareup.retrofit2:converter-gson:+")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:+")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:+")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:+")
    implementation("androidx.security:security-crypto:+")
    implementation("androidx.navigation:navigation-fragment-ktx:+")
    implementation("androidx.navigation:navigation-ui-ktx:+")
    implementation("com.squareup.okhttp3:okhttp:+")
    implementation("androidx.core:core-splashscreen:+")
    implementation("com.squareup.picasso:picasso:+")


}
