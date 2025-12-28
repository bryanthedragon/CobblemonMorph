/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PlayerActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.requests.ClientPlayerActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.resources.ResourceLocation
import java.util.*

/**
 * Clientside representation of a [PlayerActionRequest] for a battle.
 *
 * @param requestID The unique identifier of this challenge.
 * @param senderID The unique identifier of the player (or team) that sent this challenge.
 * @param expiryTime The amount of time (in seconds) this challenge is active.
 * @param battleFormat The
 *
 * @author JazzMcNade
 * @since July 22nd, 2024
 */
record ClientBattleChallenge(
    override val requestID: UUID,
    override val senderID: UUID,
    override val expiryTime: Int,
    val battleFormat: BattleFormat
) : ClientPlayerActionRequest(expiryTime) {
    override val texture: ResourceLocation = cobblemonResource("textures/particle/request/icon_challenge.png")
}