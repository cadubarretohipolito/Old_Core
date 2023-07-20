plugins {
    id("java")
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.26")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0")
    compileOnly("com.github.juliarn:npc-lib:2.6-RELEASE")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

