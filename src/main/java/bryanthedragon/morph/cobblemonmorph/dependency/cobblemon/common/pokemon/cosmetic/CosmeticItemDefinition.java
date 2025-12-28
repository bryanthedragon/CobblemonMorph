/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.cosmetic

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.ITEM_REGISTRY_LIKE_CODEC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.itemRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.usableItems
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item

// TODO: Item components on the filter and display items? Would be cool but would need to study it

class CosmeticItemAssignment(
    val pokemon: MutableList<PokemonProperties> = mutableListOf(),
    val cosmeticItems: MutableList<CosmeticItemDefinition> = mutableListOf()
) {
    @Transient
    lateinit var id: ResourceLocation

    companion object {
        val CODEC: Codec<CosmeticItemAssignment> = RecordCodecBuilder.create {
            it.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter { it.id },
                PokemonProperties.CODEC.listOf().fieldOf("pokemon").forGetter { it.pokemon },
                CosmeticItemDefinition.CODEC.listOf().fieldOf("cosmeticItems").forGetter { it.cosmeticItems }
            ).apply(it) { id, pokemon, cosmeticItems ->
                CosmeticItemAssignment(
                    pokemon,
                    cosmeticItems
                ).apply { this.id = id }
            }
        }

        val PACKET_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC)
    }
}

class CosmeticItemDefinition(
    val consumedItem: RegistryLikeCondition<Item> = RegistryLikeIdentifierCondition(cobblemonResource("poke_ball")),
    val aspects: List<String> = listOf()
) {
    companion object {
        val CODEC: Codec<CosmeticItemDefinition> = RecordCodecBuilder.create {
            it.group(
                ITEM_REGISTRY_LIKE_CODEC.fieldOf("item").forGetter { it.consumedItem },
                PrimitiveCodec.STRING.listOf().fieldOf("aspects").forGetter { it.aspects }
            ).apply(it, ::CosmeticItemDefinition)
        }

        val PACKET_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC)
    }

    fun findMatchingItemStack(player: Player) = player.inventory.usableItems().find { consumedItem.fits(it.item, player.level().itemRegistry) }
}

