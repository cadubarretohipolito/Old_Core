import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"

}

group = "br.cadu.pro"
version = "1.0-SNAPSHOT"

allprojects{
    apply(plugin = "java")
    apply(plugin = "java-library")


    repositories {
        mavenCentral()
        // Paper Repo
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        // Jitpack
        maven("https://jitpack.io")
        //ProtocolLib Repo, constantly down
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://repo.mineinabyss.com/releases/")
        maven("https://repo.mineinabyss.com/snapshots/")
        maven("https://repo.md-5.net/content/groups/public/")
    }

    dependencies{
        compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
        compileOnly("org.jetbrains:annotations:23.0.0")
        compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")
        compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0")
    }

}

dependencies {
    implementation(project(path = ":common"))
    implementation(project(path = ":v1_19_R3", configuration = "reobf"))
}

tasks {

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(16)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = Charsets.UTF_8.name()
    }
    runServer {
        minecraftVersion("1.19.4")
    }

    shadowJar {
        dependsOn(":v1_19_R3:reobfJar")
        mergeServiceFiles()

        archiveFileName.set("SkyBlockCore.jar")
        destinationDirectory.set(file("C:/Users/ce641/Documents/project/server-skyblock/plugins"))

        dependencies {
            exclude(dependency("org.yaml:snakeyaml"))
        }

    }

    build {
        dependsOn(shadowJar)
    }
}

bukkit {
    main = "br.cadu.pro.master.Main"
    apiVersion = "1.16"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    authors = listOf("CarlosMasterPr0")

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

