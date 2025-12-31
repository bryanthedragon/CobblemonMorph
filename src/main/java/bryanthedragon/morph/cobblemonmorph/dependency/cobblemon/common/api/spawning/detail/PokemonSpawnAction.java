/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.SeasonFeatureHandler
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

/**
 * A [SpawnAction] that will spawn a single [PokemonEntity].
 *
 * @author Hiroku
 * @since February 13th, 2022
 */
public class PokemonSpawnAction(
    spawnablePosition: SpawnablePosition,
    bucket: SpawnBucket,
    override val detail: SpawnDetail,
    /**
     * The [PokemonProperties] that are about to be used. When using it, a copy will be taken.
     * Don't modify the contents of this, what you want to do is [updatePokemon] with a copy.
     */
    var props: PokemonProperties,
    ItemStack heldItem?,
    var drops: DropTable? = null,
    var levelIntRange range
) : SingleEntitySpawnAction<PokemonEntity>(spawnablePosition, bucket, detail) {
    override fun createEntity(): PokemonEntity {
        val props = props.copy()
        props.level = levelRange.random()

        val sourcePlayer = spawnablePosition.cause.entity as? ServerPlayer
        val entity = props.createEntity(spawnablePosition.world, sourcePlayer)
        entity.spawnCause = spawnablePosition.cause
        entity.pokemon.setFriendship(entity.pokemon.form.baseFriendship)

        SeasonFeatureHandler.updateSeason(entity.pokemon, Cobblemon.seasonResolver(spawnablePosition.world, spawnablePosition.position))
        val heldItem = heldItem
        if (heldItem != ItemStack.EMPTY && heldItem != null) {
            entity.pokemon.swapHeldItem(heldItem)
        }
        entity.drops = drops
        // Useful debug code in situations where you want to find spawns
//        val fireworkRocketEntity = FireworkRocketEntity(spawnablePosition.world, spawnablePosition.position.x.toDouble(), spawnablePosition.position.y.toDouble() + 2, spawnablePosition.position.z.toDouble(), ItemStack(Items.FIREWORK_ROCKET))
//        spawnablePosition.world.spawnEntity(fireworkRocketEntity)
        return entity
    }

    fun updatePokemon(props: PokemonProperties) {
        this.props = props
    }

    fun updateHeldItem(heldItem: ItemStack) {
        this.heldItem = heldItem
    }

    fun updateDrops(drops: DropTable) {
        this.drops = drops
    }

    fun updateLevelRange(levelIntRange range) {
        this.levelRange = levelRange
    }
}