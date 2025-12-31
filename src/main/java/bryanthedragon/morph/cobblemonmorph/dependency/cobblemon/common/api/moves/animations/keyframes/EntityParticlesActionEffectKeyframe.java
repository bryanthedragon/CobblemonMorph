/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.TargetsProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.delayedFuture
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PosableEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import net.minecraft.commands.arguments.EntityArgument.getPlayers
import java.util.concurrent.CompletableFuture
import net.minecraft.server.level.ServerLevel

/**
 * Spawns particles on the entity based on the given particle effect and locator.
 * Optionally, you can specify target that ends up setting the destination pos on the spawned ParticleStorm
 *
 * @author Hiroku
 * @since January 21st, 2024
 */
public class EntityParticlesActionEffectKeyframe : ConditionalActionEffectKeyframe(), EntityConditionalActionEffectKeyframe {
    override val entityCondition = "q.entity.is_user".asExpressionLike()
    var effect: String? = null
    var locators: List<String> = listOf("target")
    var targetLocators: Set<String>? = null

    val delay: ExpressionLike = "0".asExpressionLike()
    val visibilityRange = 200

    override fun playWhenTrue(context: ActionEffectContext): CompletableFuture<Unit> {
        val allEntities = context.providers
            .filterIsInstance<EntityProvider>()
            .flatMap { prov -> prov.entities }

        val userEntities = context.providers
            .filterIsInstance<UsersProvider>()
            .flatMap { prov -> prov.entities }

        val targetEntities = context.providers
            .filterIsInstance<TargetsProvider>()
            .flatMap { prov -> prov.entities}

        val sourceEntities = allEntities.filter { test(context, it, it in userEntities) }

        val effectIdentifier = try {
            effect?.asExpressionLike()?.resolveString(context.runtime)?.takeIf { it != "0" } ?: effect
        } catch (Exception e) {
            effect
        }?.asIdentifierDefaultingNamespace() ?: return skip()

        //Things could be a little weird here since TECHNICALLY it might be possible for a battle to be happening across dimensions
        val calcedLevel = (context.level as? ServerLevel) ?: allEntities.first().level() as ServerLevel
        val players = calcedLevel.getPlayers { player ->
            allEntities.any { player.distanceTo(it) <= visibilityRange }
        }


        sourceEntities.filter { it is PosableEntity }.forEach { entity ->
            if (targetLocators == null) {
                val packet = SpawnSnowstormEntityParticlePacket(effectIdentifier, entity.id, locators)
                packet.sendToPlayers(players)
            }
            else {
                targetEntities.forEach { targetEntity ->
                    val packet = SpawnSnowstormEntityParticlePacket(effectIdentifier, entity.id, locators, targetEntity.id, targetLocators!!.toList())
                    packet.sendToPlayers(players)
                }
            }
        }

        return delayedFuture(seconds = delay.resolveFloat(context.runtime))
    }
}