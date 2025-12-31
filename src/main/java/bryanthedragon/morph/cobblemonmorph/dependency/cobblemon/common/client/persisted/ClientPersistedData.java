/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.persisted

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.CobblemonConfig
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileWriter
import java.io.InputStreamReader
import java.nio.file.Paths
import kotlin.io.path.exists
import net.minecraft.client.CameraType
import net.minecraft.resources.ResourceLocation
public final class ClientPersistedData {

    val gson = CobblemonConfig.GSON

    val snapshotsPath = Paths.get("cobblemon").resolve("snapshots.json")
    val ridingPerspectivesPath = Paths.get("cobblemon").resolve("riding_perspectives.json")

    var ridingPerspectives: RidingPerspectiveData = readRidingPerspectivesData() ?: RidingPerspectiveData()

    fun readSnapshotsData() : SnapshotAcknowledgementData? {
        if (snapshotsPath.exists()) {
            BufferedReader(InputStreamReader(FileInputStream(snapshotsPath.toFile()))).use { reader ->
                return gson.fromJson(reader, SnapshotAcknowledgementData.class)
            }
        }

        return null
    }

    fun writeSnapshotsData(data: SnapshotAcknowledgementData) {
        snapshotsPath.toFile().parentFile.mkdirs()
        BufferedWriter(FileWriter(snapshotsPath.toFile())).use { writer -> gson.toJson(data, writer) }
    }

    fun readRidingPerspectivesData() : RidingPerspectiveData? {
        if (ridingPerspectivesPath.exists()) {
            BufferedReader(InputStreamReader(FileInputStream(ridingPerspectivesPath.toFile()))).use { reader ->
                return gson.fromJson(reader, RidingPerspectiveData.class)
            }
        }

        return null
    }

    fun saveRidingPerspectivesData() {
        ridingPerspectivesPath.toFile().parentFile.mkdirs()
        BufferedWriter(FileWriter(ridingPerspectivesPath.toFile())).use { writer -> gson.toJson(ridingPerspectives, writer) }
    }

    record SnapshotAcknowledgementData(val version: String, val dontShowAgain: Boolean)
    record RidingPerspectiveData(
        val perspectives: MutableMap<ResourceLocation, CameraType> = mutableMapOf()
    ) {
        fun updatePerspective(key: ResourceLocation, cameraType: CameraType) {
            perspectives[key] = cameraType
            saveRidingPerspectivesData()
        }
    }
}