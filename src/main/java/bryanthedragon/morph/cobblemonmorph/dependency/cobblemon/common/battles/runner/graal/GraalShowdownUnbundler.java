/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.FileUtils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.extractTo
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.fromJson
import com.google.gson.GsonBuilder
import net.minecraft.resources.ResourceLocation
import oshi.util.FileUtil
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Unbundles a zipped Showdown file found within {@code resources/data/showdown.zip} for use within the GraalShowdownService.
 * This will do a metadata check prior to unbundling and stop if there is already an unbundled showdown package
 * of the same version.
 *
 * @since  February 27, 2023
 * @author Deltric
 */
public class GraalShowdownUnbundler {

    private val gson = GsonBuilder()
        .disableHtmlEscaping()
        .create()

    fun attemptUnbundle() {
        val showdownDir = File("showdown")
        val metadata = loadShowdownMetadata()

        // Check if showdown needs to be installed
        if (!showdownDir.exists() || Cobblemon.config.autoUpdateShowdown) {
            showdownDir.mkdirs()

            val showdownZip = File(showdownDir, "showdown.zip")
            val showdownMetadataFile = File(showdownDir, "showdown.json")

            var extract = true
            if (showdownMetadataFile.exists()) {
                val current = this.readShowdownMetadata(showdownMetadataFile)
                if (metadata!!.showdownVersion == current!!.showdownVersion) {
                    extract = false
                } else {
                    // Backup current install first before continuing
                    Cobblemon.LOGGER.info("Updating showdown service to version ${metadata.showdownVersion}, from version ${current.showdownVersion}...")

                    val backupDir = File("showdown-backup")
                    if (backupDir.exists() && backupDir.isDirectory) {
                        backupDir.deleteRecursively()
                    }

                    showdownDir.copyTo(backupDir)
                }
            }

            if (extract) {
                FileUtils.copyInternalToExternal("/data/${Cobblemon.MODID}/showdown.zip", showdownZip.toPath())
                FileUtils.copyInternalToExternal("/data/${Cobblemon.MODID}/showdown.json", showdownMetadataFile.toPath())
                FileUtils.unzipFile(showdownZip.toPath(), showdownDir.toPath())
                showdownZip.delete()
            }
        }
    }

    private fun loadShowdownMetadata() : ShowdownMetadata? {
        try {
            val inputStream = javaClass.getResourceAsStream("/data/${Cobblemon.MODID}/showdown.json")!!
            return gson.fromJson<ShowdownMetadata>(InputStreamReader(inputStream))
        } catch (Exception exception) {
            exception.printStackTrace()
        }
        return null
    }

    private fun readShowdownMetadata(target: File) : ShowdownMetadata? {
        try {
            InputStreamReader(FileInputStream(target)).use {
                return gson.fromJson<ShowdownMetadata>(it)
            }
        } catch (Exception exception) {
            exception.printStackTrace()
            return null
        }
    }

    private record ShowdownMetadata(val showdownVersion: Double)

}