plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.bookstoreapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bookstoreapplication"
        minSdk = 34
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
    sourceSets {
        getByName("androidTest") {
            java.srcDir("src/androidTest/java")
            resources.srcDir("src/androidTest/resources")
        }
    }

    defaultConfig {
        // Add test instrumentation runner
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // Firebase Authentication
    androidTestImplementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    testImplementation("com.google.firebase:firebase-auth:23.0.0")
    // Firebase Storage
    implementation(libs.firebase.storage)

    // Firebase Realtime Database
    implementation(libs.firebase.database)

    // Test Dependencies
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.5.21")

    // Android Test Dependencies
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.firebase.auth)
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("org.mockito:mockito-core:5.12.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
}
