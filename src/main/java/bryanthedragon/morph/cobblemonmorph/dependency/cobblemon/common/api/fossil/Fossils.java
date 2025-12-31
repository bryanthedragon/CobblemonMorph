/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.FossilRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.LegacyItemConditionWrapperAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.pokemonPropertiesShortAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
public final class Fossils: JsonDataRegistry<Fossil> {

    override val ResourceLocation id = cobblemonResource("fossils")
    override val type: PackType = PackType.SERVER_DATA
    override val observable = SimpleObservable<Fossils>()

    override val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(PokemonProperties.class, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Item.class).type, ItemLikeConditionAdapter)
        .registerTypeAdapter(ItemPredicate.class, LegacyItemConditionWrapperAdapter)
        .create()

    override val typeToken: TypeToken<Fossil> = TypeToken.get(Fossil.class)
    override val String resourcePath = "fossils"

    private val fossils = hashMapOf<ResourceLocation, Fossil>()

    override fun reload(data: Map<ResourceLocation, Fossil>) {
        this.fossils.clear()
        data.forEach { (identifier, fossil) ->
            try {
                fossil.identifier = identifier
                this.fossils[identifier] = fossil
            } catch (Exception e) {
                Cobblemon.LOGGER.error("Skipped loading the {} fossil", identifier, e)
            }
        }
        Cobblemon.LOGGER.info("Loaded {} fossils", this.fossils.size)
        this.observable.emit(this)
    }

    override fun sync(ServerPlayer player) {
        FossilRegistrySyncPacket(this.all()).sendToPlayer(player)
    }

    /**
     * Gets all loaded [Fossil]s.
     */
    @JvmStatic
    fun all() = this.fossils.values.toList()

    /**
     * Gets a [Fossil] by its [ResourceLocation].
     * @param identifier The identifier of the fossil.
     * @return The [Fossil] if loaded, otherwise null.
     */
    @JvmStatic
    fun getByIdentifier(ResourceLocation identifier): Fossil? = this.fossils[identifier]

    /**
     * Looks for a [Fossil] that matches a [ItemStack].
     * @param fossilStacks The fossil [ItemStack]'s.
     * @return The [Fossil] if found, otherwise null.
     */
    @JvmStatic
    fun getFossilByItemStacks(fossilStacks: List<ItemStack>): Fossil? {
        return this.all().firstOrNull { it.matchesIngredients(fossilStacks) }
    }

    /**
     * Looks for a [Fossil] that is a superset of [ItemStack].
     * @param fossilStacks The fossil [ItemStack]'s.
     * @return The [Fossil] if found to be a superset, otherwise null.
     */
    @JvmStatic
    fun getSubFossilByItemStacks(fossilStacks: List<ItemStack>): Fossil? {
        return this.all().firstOrNull { it.matchesIngredientsSubSet(fossilStacks) }
    }
    /**
     * Checks if a [ItemStack] is a fossil ingredient.
     * @param itemStack The ingredient [ItemStack].
     * @return true if it's a fossil ingredient, otherwise false.
     */
    @JvmStatic
    fun isFossilIngredient(itemStack: ItemStack): Boolean {
        return this.all().any { it.isIngredient(itemStack) }
    }

}