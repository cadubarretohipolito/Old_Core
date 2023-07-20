plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.1"
}

dependencies {

    implementation("org.projectlombok:lombok:1.18.26")
    implementation(project(":common"))
    paperDevBundle("1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.19.4-R0.1-SNAPSHOT")

}

tasks {

    build {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}