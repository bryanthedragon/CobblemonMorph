/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.BattleExperienceSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import java.util.UUID
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.phys.Vec3

public class PlayerBattleActor(
    UUID uuid,
    pokemonList: List<BattlePokemon>,
) : BattleActor(uuid, pokemonList.toMutableList()), EntityBackedBattleActor<ServerPlayer> {

    override val initialPos: Vec3?
    override val entity: ServerPlayer?
        get() = this.uuid.getPlayer()
    init {
        initialPos = entity?.position();
    }

    /** The [ResourceLocation] to play to the player during a battle. Will start playing as soon as the battle starts. */
    var battleTheme: ResourceLocation? = null
        set(value) {
            if (this.isInitialized() && this.battle.started) this.sendUpdate(BattleMusicPacket(value))
            field = value
        }

    override fun getName(): MutableComponent = this.entity?.name?.copy() ?: "Offline Player".red()
    override fun nameOwned(String name): MutableComponent = battleLang("owned_pokemon", this.getName(), name)
    override val type = ActorType.PLAYER
    override fun getPlayerUUIDs() = setOf(uuid)
    override fun awardExperience(BattlePokemon battlePokemon, experience: Int) {
        if (battle.isPvP && !Cobblemon.config.allowExperienceFromPvP) {
            return
        }

        val source = BattleExperienceSource(battle, battlePokemon.facedOpponents.toList())
        if (battlePokemon.effectedPokemon == battlePokemon.originalPokemon && experience > 0) {
            uuid.getPlayer()
                ?.let { battlePokemon.effectedPokemon.addExperienceWithPlayer(it, source, experience) }
                ?: run { battlePokemon.effectedPokemon.addExperience(source, experience) }
        }
    }

    override fun sendUpdate( NetworkPacket<*> packet) {
        CobblemonNetwork.sendPacketToPlayers(getPlayerUUIDs().mapNotNull { it.getPlayer() }, packet)
    }
}
