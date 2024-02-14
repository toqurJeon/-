// build.gradle.kts 파일의 경우, 잘못된 임포트 문장을 제거해야 합니다.
// import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot.classpath

buildscript {
    // Kotlin 버전을 정의합니다. 이 값을 다른 곳에서 가져오거나, 직접 값을 할당해야 합니다.
    // 예를 들어 프로젝트의 root build.gradle.kts 파일에 ext 블록을 사용하여 정의할 수 있습니다.
    val kotlin_version = "1.9.22"  // 변수 선언에는 콜론(:)을 사용하지 않고, 직접 값을 할당합니다.

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

plugins {
    // 적용(apply) 플래그를 false로 설정하여 플러그인을 조건부로 적용할 수 있습니다.
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    // 구글 액세스 토큰
    id("com.google.gms.google-services") version "4.4.0" apply false
    kotlin("plugin.parcelize") version "1.6.10"  apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

allprojects {
    repositories {
        google()
        jcenter()
//        maven { url("https://jitpack.io") }
    }
}
