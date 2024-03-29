plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("androidx.navigation.safeargs")
	id("kotlin-android")
	id("com.google.devtools.ksp")
	id("dagger.hilt.android.plugin")
	id("kotlin-parcelize")
}

android {
	namespace = "com.glitch.excuser"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.glitch.excuser"
		minSdk = 24
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
	buildFeatures {
		viewBinding = true
		//noinspection DataBindingWithoutKapt
		dataBinding = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.11.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.6.2")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	//Navigation
	implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
	implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
	//Glide - Lottie
	implementation ("com.github.bumptech.glide:glide:4.16.0")
	implementation ("com.airbnb.android:lottie:6.3.0")
	//Retrofit
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")
	implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation ("com.google.code.gson:gson:2.10.1")
	//http
	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	//Hilt
	implementation("com.google.dagger:hilt-android:2.50")
	ksp("com.google.dagger:hilt-compiler:2.50")
	ksp("androidx.hilt:hilt-compiler:1.1.0")
	implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")
	//Room
	implementation("androidx.room:room-runtime:2.6.1")
	ksp("androidx.room:room-compiler:2.6.1")
	implementation("androidx.room:room-ktx:2.6.1")
	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
	// Lifecycle
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
	implementation("androidx.activity:activity-ktx:1.8.2")
}