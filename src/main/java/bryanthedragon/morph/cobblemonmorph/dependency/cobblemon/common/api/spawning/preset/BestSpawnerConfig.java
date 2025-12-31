/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.BestSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePositionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegisteredSpawnablePositionAdapter
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.InputStreamReader

/**
 * The config class for everything related to the [BestSpawner]. This is loaded immediately after the
 * main mod config.
 *
 * @author Hiroku
 * @since July 8th, 2022
 */
public class BestSpawnerConfig {
    val version = 0
    /** Whether an external config will be replaced by an internal one once [version] is higher on the internal. */
    val replaceWithNewVersion = true
    @SerializedName("spawnablePositionTypeWeights", alternate = ["contextWeights"])
    val spawnablePositionTypeWeights = mutableMapOf(
        "grounded" to 1F,
        "submerged" to 0.99F,
        "surface" to 0.01F,
        "seafloor" to 1F
    )
    val buckets = mutableListOf(
        SpawnBucket("common", 94.3F),
        SpawnBucket("uncommon", 5F),
        SpawnBucket("rare", 0.5F),
        SpawnBucket("ultra-rare", 0.2F)
    )

    final class Companion {
        val GSON = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(SpawnablePositionType.class, RegisteredSpawnablePositionAdapter)
            .setLenient()
            .disableHtmlEscaping()
            .create()

        const val CONFIG_NAME = "best-spawner-config"
        const val INTERNAL_PATH = "/data/${Cobblemon.MODID}/spawning/$CONFIG_NAME.json"
        const val EXTERNAL_PATH = "config/${Cobblemon.MODID}/spawning/$CONFIG_NAME.json"

        fun load(): BestSpawnerConfig {
            val internal = loadInternal()
            if (Cobblemon.config.exportSpawnConfig) {
                val external = loadExternal()
                return if (external == null) {
                    saveExternal()
                    internal
                } else {
                    if (external.replaceWithNewVersion && internal.version > external.version) {
                        saveExternal()
                        internal
                    } else {
                        external
                    }
                }
            } else {
                return internal
            }
        }

        private fun loadInternal(): BestSpawnerConfig {
            val reader = InputStreamReader(Cobblemon.class.getResourceAsStream(INTERNAL_PATH)!!)
            val config = GSON.fromJson(reader, BestSpawnerConfig.class)
            reader.close()
            return config
        }

        private fun loadExternal(): BestSpawnerConfig? {
            val configFile = File(EXTERNAL_PATH)
            configFile.parentFile.mkdirs()
            return if (configFile.exists()) {
                try {
                    val reader = FileReader(configFile)
                    val config = GSON.fromJson(reader, BestSpawnerConfig.class)
                    reader.close()
                    config
                } catch (Exception e) {
                    LOGGER.error("Unable to load external Best Spawner configuration", e)
                    null
                }
            } else {
                null
            }
        }

        fun saveExternal() {
            val stream = Cobblemon.class.getResourceAsStream(INTERNAL_PATH)!!
            val bytes = stream.readAllBytes()
            stream.close()
            val configFile = File(EXTERNAL_PATH)
            configFile.parentFile.mkdirs()
            configFile.createNewFile()
            val outputStream = FileOutputStream(configFile)
            outputStream.write(bytes)
            outputStream.close()
        }
    }
}
