/*
 *
 *  * Copyright (C) 2023 Cobblemon Contributors
 *  *
 *  * This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *  * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 */

plugins {
    id("net.kyori.blossom") version "1.3.1"
    id("architectury-plugin") version "3.4.162"
    id("dev.architectury.loom") version "1.2.377"
    id 'eclipse'
    id 'idea'
    id 'maven-publish'
    id 'net.minecraftforge.gradle' version '[6.0.16,6.2)'

    // Only edit below this line, the above code adds and enables the necessary things for Forge to be setup.
    // due to mixin mixing in craftedcore interfering with below code causing errors when building only use below code if absolutely necessary
    id 'org.parchmentmc.librarian.forgegradle' version '1.+'
}

architectury {
    common("forge", "fabric")
}

repositories {
    maven(url = "${rootProject.projectDir}/deps")
    maven(url = "https://api.modrinth.com/maven")
    mavenLocal()
}

dependencies {
    implementation(libs.bundles.kotlin)
    modImplementation(libs.fabric.loader)
    modApi(libs.molang)
    compileOnlyApi(libs.jei.api)
    modCompileOnly(libs.bundles.fabric.integrations.compileOnly) { isTransitive = false }
    modCompileOnly(libs.graal)
    modCompileOnly(libs.bundles.mongo)
    testImplementation(libs.bundles.unitTesting)
}

loom {
    accessWidenerPath.set(file("src/main/resources/cobblemon.accesswidener"))

    // Set the Minecraft version
    minecraft("1.20.1")

    // Use Parchment mappings instead of official
    mappings("parchment", "2026.2.27") // pick the correct Parchment version for 1.20.1

    // Enable copying IDE resources (similar to copyIdeResources = true in Forge)
    runConfigs {
        create("client") {
            workingDirectory = file("run")
            jvmArgs("-Dmixin.debug.verbose=true")
            jvmArgs("-Dmixin.hotSwap=true")
            jvmArgs("-Dcraftedcore.disableMixins=true")
        }

        create("server") {
            workingDirectory = file("run")
            args("--nogui")
        }

        create("data") {
            workingDirectory = file("run-data")
            args("--mod", "cobblemonmorph", "--all",
                 "--output", file("src/generated/resources/"),
                 "--existing", file("src/main/resources/"))
        }
    }
}

sourceSets {
    main {
        blossom {
            kotlinSources {
                val header = buildString {
                    append("/*\n")
                    rootProject.file("HEADER").forEachLine { line ->
                        append(" * ").appendLine(line)
                    }
                    append(" */\n")
                }

                extra["license"] = header
                extra["modid"] = "cobblemon"
                extra["version"] = project.version.toString()
                extra["isSnapshot"] = project.version.toString().endsWith("-SNAPSHOT")
                System.getProperty("buildNumber")?.let { extra["buildNumber"] = it }
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        setEvents(listOf("failed"))
        setExceptionFormat("full")
    }
}