plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  buildFeatures { compose = true }

  namespace = "org.MdmSystemTools.Application"
  compileSdk = 36

  defaultConfig {
    applicationId = "org.mdm_system_tools.application"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  kotlin { jvmToolchain(21) }
  buildFeatures { compose = true }
}

dependencies {
  implementation(libs.androidx.compose.ui.text)
  implementation(libs.androidx.lifecycle.viewmodel.savedstate)
  val composeBom = platform("androidx.compose:compose-bom:2025.11.00")
  implementation(composeBom)
  testImplementation(composeBom)
  androidTestImplementation(composeBom)

  // Room
  implementation(libs.room.runtime)
  ksp(libs.room.compiler)
  implementation(libs.room.ktx)

  // Room Tests
  testImplementation(libs.room.testing)
  androidTestImplementation(libs.room.testing)

  // Android Tests
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  // Java/Kotlin Tests
  testImplementation(libs.junit)

  // Jetpack Compose
  debugImplementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.animation)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.ktx)

  // navigation
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.material3.adaptive.navigation.suite)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.hilt.android)

  // Serialization
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.transport.runtime)
  ksp(libs.hilt.android.compiler)
  testImplementation(kotlin("test"))
}
