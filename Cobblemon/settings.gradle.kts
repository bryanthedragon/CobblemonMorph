enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.architectury.dev/")   // Architectury SNAPSHOTs
        gradlePluginPortal()                        // normal plugins like Blossom
        mavenCentral()
        maven("https://maven.minecraftforge.net/")  // Forge
        maven("https://maven.fabricmc.net/")       // <--- FabricMC artifacts for Loom
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "net.kyori.blossom") {
                useModule("net.kyori:blossom:${requested.version}")
            }
        }
    }
}

rootProject.name = "cobblemon"

listOf("common", "forge").forEach { 
    setupProject(it, file(it)) 
}

fun setupProject(name: String, projectDirectory: File) = setupProject(name) {
    projectDir = projectDirectory
}

inline fun setupProject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}