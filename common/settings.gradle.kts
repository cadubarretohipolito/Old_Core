pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://repo.dmulloy2.net/repository/public/")
    }
}

rootProject.name = "SkyBlockCore"
include(
    "common",
    "v1_19_R3",
}