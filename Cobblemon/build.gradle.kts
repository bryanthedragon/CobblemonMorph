plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("dev.architectury.loom") version "1.2-SNAPSHOT" // <--- VERSION REQUIRED
    id("net.kyori.blossom") version "1.3.1"
    // id ("net.nemerosa.versioning") version "2.8.2" // package is a dead repository, so we have to kill it due to that and that the maintainers of the maven have killed the maven link we are just going to disable it for now, we can re-enable it when they fix the maven link  
}

group = "com.cobblemon.mod"
version = "${project.property("mod_version")}+${project.property("mc_version")}"