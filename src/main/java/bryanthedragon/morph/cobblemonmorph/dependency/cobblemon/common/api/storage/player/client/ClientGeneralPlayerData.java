/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.GeneralPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.SetClientPlayerDataPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import java.util.UUID

/**
 * Client representation of [GeneralPlayerData]
 *
 * @author Apion
 * @since February 21, 2024
 */

record ClientGeneralPlayerData(
    val resetStarters: Boolean? = false,
    var promptStarter: Boolean = true,
    var starterLocked: Boolean = true,
    var starterSelected: Boolean = false,
    var starterUUID: UUID? = null,
    var showChallengeLabel: Boolean = true,
    val battleTheme: ResourceLocation? = null
) : ClientInstancedPlayerData {

    override fun encode(buf: RegistryFriendlyByteBuf) {
        buf.writeBoolean(promptStarter)
        buf.writeBoolean(starterLocked)
        buf.writeBoolean(starterSelected)
        buf.writeBoolean(showChallengeLabel)
        val starterUUID = starterUUID
        buf.writeNullable(starterUUID) { pb, value -> pb.writeString(value.toString()) }
        buf.writeNullable(resetStarters) { pb, value -> pb.writeBoolean(value) }
        buf.writeNullable(battleTheme) {pb, value -> pb.writeIdentifier(value)}
    }
    companion object {
        fun decode(buffer: RegistryFriendlyByteBuf): SetClientPlayerDataPacket {

            val promptStarter = buffer.readBoolean()
            val starterLocked = buffer.readBoolean()
            val starterSelected = buffer.readBoolean()
            val showChallengeLabel = buffer.readBoolean()
            val starterUUID = buffer.readNullable { it.readString() }?.let { UUID.fromString(it) }
            val resetStarterPrompt = buffer.readNullable { it.readBoolean() }
            val battleTheme = buffer.readNullable { it.readIdentifier() }
            val data = ClientGeneralPlayerData(
                resetStarterPrompt,
                promptStarter,
                starterLocked,
                starterSelected,
                starterUUID,
                showChallengeLabel,
                battleTheme
            )
            //Weird to do this, but since the flag doesn't get passed to the decoded obj, do it here
            //Should be fine, as long as decode doesn't get run on the server for some reason
            if (resetStarterPrompt == true) {
                CobblemonClient.checkedStarterScreen = false
                CobblemonClient.overlay.resetAttachedToast()
            }
            return SetClientPlayerDataPacket(PlayerInstancedDataStoreTypes.GENERAL, data)
        }

        fun runAction(data: ClientInstancedPlayerData) {
            if (data !is ClientGeneralPlayerData) return
            CobblemonClient.clientPlayerData = data
        }
    }
}