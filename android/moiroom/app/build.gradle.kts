// Gradle:  Android 애플리케이션 및 기타 프로젝트의 빌드와 종속성 관리를 위한 강력한 빌드 도구

// 프로젝트에 적용할 플러그인
// 플러그인: Gradle 빌드 도구의 확장 기능으로, 특정 기능이나 작업을 수행할 수 있도록 도와주는 도구나 라이브러리
// 기본 구조: id("plugin-id") version "plugin-version"
plugins {
    // 안드로이드 어플 개발 플러그인
    id("com.android.application")
    // 코틀린을 통한 안드로이드 어플 개발 플러그인
    id("org.jetbrains.kotlin.android")
    // 코틀린 어노테이션 프로세싱 플러그인 (아직 정확히 모르겠음)
    // 어노테이션: @ 뒤에 있는 거
    id("kotlin-kapt")
}


android {
    namespace = "com.example.moiroom"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moiroom"
        minSdk = 30
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

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // retrofit, gson-converter
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // 카카오 로그인 모듈 추가
    implementation("com.kakao.sdk:v2-user:2.0.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    // 갤러리 접근용
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // viewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

}