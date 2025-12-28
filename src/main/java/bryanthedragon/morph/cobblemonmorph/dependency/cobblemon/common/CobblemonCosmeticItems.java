/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.CosmeticItemAssignmentSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.cosmetic.CosmeticItemAssignment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
final class CobblemonCosmeticItems : JsonDataRegistry<CosmeticItemAssignment> {
    override val id = cobblemonResource("cosmetic_items")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<CobblemonCosmeticItems>()
    override val typeToken = TypeToken.get(CosmeticItemAssignment::class.java)
    override val resourcePath = "cosmetic_items"

    override val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(PokemonProperties::class.java, PokemonPropertiesAdapter(saveLong = false))
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition::class.java, Item::class.java).type, ItemLikeConditionAdapter)
        .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter)
        .create()

    @JvmField
    val cosmeticItems = mutableListOf<CosmeticItemAssignment>()

    override fun reload(data: Map<ResourceLocation, CosmeticItemAssignment>) {
        cosmeticItems.clear()
        data.entries.forEach { (id, value) -> value.id = id }
        cosmeticItems.addAll(data.values)
    }

    override fun sync(player: ServerPlayer) {
        CosmeticItemAssignmentSyncPacket(cosmeticItems).sendToPlayer(player)
    }

    @JvmStatic
    fun findValidForPokemon(pokemon: Pokemon) = cosmeticItems.filter { it.pokemon.any { it.matches(pokemon) } }
    @JvmStatic
    fun findValidCosmeticForPokemonAndItem(registryAccess: RegistryAccess, pokemon: Pokemon, itemStack: ItemStack) = cosmeticItems
        .filter { it.pokemon.any { it.matches(pokemon) } }
        .flatMap { it.cosmeticItems }
        .firstOrNull { it.consumedItem.fits(itemStack.item, registryAccess.registryOrThrow(Registries.ITEM)) }
}