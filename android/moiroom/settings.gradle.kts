pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// 카카오 sdk 레포지토리 설정
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        // MPAndroidChart
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "moiroom"
include(":app")
 