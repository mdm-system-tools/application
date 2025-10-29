plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  buildFeatures {
    compose = true
  }

  namespace = "org.MdmSystemTools.Application"
  compileSdk = 36

  defaultConfig {
    applicationId = "org.MdmSystemTools.Application"
    minSdk = 24
    targetSdk = 36
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
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  kotlin {
      jvmToolchain(21)
  }
  buildFeatures {
    compose = true
  }
}

dependencies {
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.ui.test.junit4)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  ksp("com.google.dagger:hilt-android-compiler:2.57.1")
  implementation("androidx.activity:activity-compose:1.8.2")
  implementation("androidx.compose.material:material-icons-extended:1.7.7")
  implementation("androidx.compose.material:material:1.9.2")
  implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.3")
  implementation("androidx.navigation:navigation-compose:2.9.4")
  implementation("com.google.dagger:hilt-android-testing:2.57.1")
  implementation("com.google.dagger:hilt-android:2.57.1")
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.animation)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.foundation.layout)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.material3.adaptive.navigation.suite)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.material3)
  implementation(libs.transport.runtime)
  implementation(platform(libs.androidx.compose.bom))
  testImplementation(kotlin("test"))
  testImplementation(libs.junit)
  debugImplementation(libs.androidx.ui.test.manifest)
  debugImplementation(libs.androidx.ui.tooling)
}