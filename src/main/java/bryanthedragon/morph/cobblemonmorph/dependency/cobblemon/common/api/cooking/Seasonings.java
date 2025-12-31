/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitUtils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.components.FoodComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.cooking.SeasoningRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
public final class Seasonings : JsonDataRegistry<Seasoning> {
    override val id = cobblemonResource("seasonings")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<Seasonings>()
    override val typeToken: TypeToken<Seasoning> = TypeToken.get(Seasoning.class)
    override val resourcePath = "seasonings"
    override val Gson gson = GsonBuilder()
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Item.class).type, ItemLikeConditionAdapter)
        .setPrettyPrinting()
        .create()

    val seasonings = mutableListOf<Seasoning>()

    override fun sync(ServerPlayer player) {
        SeasoningRegistrySyncPacket(seasonings.toList()).sendToPlayer(player)
    }

    override fun reload(data: Map<ResourceLocation, Seasoning>) {
        // this needs to sideload the berry data so we don't get duplicate JSONs.
        val finalData = Berries.all().associate {
            it.identifier to Seasoning(
                ingredient = RegistryLikeIdentifierCondition(it.identifier),
                flavours = it.flavours.toMap(),
                colour = it.colour,
                baitEffects = emptyList(),
                food = Food(),
                mobEffects = emptyList()
            )
        }.toMutableMap()
        finalData.putAll(data)
        seasonings.addAll(finalData.values)
    }

    fun reloadEntries(seasonings: Collection<Seasoning>) {
        this.seasonings.clear()
        this.seasonings.addAll(seasonings)
    }

    fun getFlavoursFromItemStack(ItemStack stack): Map<Flavour, Int>? {
        val holder = stack.itemHolder
        val seasoning = seasonings.find { it.ingredient.fits(holder) }
        val inherentFlavours = stack.get(CobblemonItemComponents.FLAVOUR)?.flavours
        val seasoningFlavours = seasoning?.flavours

        if (seasoningFlavours.isNullOrEmpty() && inherentFlavours.isNullOrEmpty())
            return null

        return (seasoningFlavours ?: emptyMap()).mapValues { (flavour, value) ->
            value + (inherentFlavours?.getOrDefault(flavour, 0) ?: 0)
        }
    }

    fun hasFlavors(ItemStack stack): Boolean {
        val flavors = getFlavoursFromItemStack(stack)
        return !flavors.isNullOrEmpty() && flavors.any { it.value != 0 }
    }

    fun getFoodComponentFromItemStack(ItemStack stack): FoodComponent? {
        return getFromItemStack(stack)?.food?.toComponent()
    }

    fun hasFood(ItemStack stack): Boolean {
        val effects = getFromItemStack(stack)?.food ?: return false
        return effects.hunger > 0 || effects.saturation > 0f
    }

    fun getMobEffectsFromItemStack(ItemStack stack): List<SerializableMobEffectInstance> {
        return getFromItemStack(stack)?.mobEffects ?: emptyList()
    }

    fun hasMobEffect(ItemStack stack): Boolean {
        return !getFromItemStack(stack)?.mobEffects.isNullOrEmpty()
    }

    fun getBaitEffectsFromItemStack(ItemStack stack): List<SpawnBait.Effect> {
        val primaryEffects = SpawnBaitEffects.getEffectsFromItemStack(stack)
        return if (primaryEffects.isNotEmpty()) {
            primaryEffects
        } else {
            getFromItemStack(stack)?.baitEffects ?: emptyList()
        }
    }

    fun hasBaitEffects(ItemStack stack): Boolean {
        return SpawnBaitEffects.getEffectsFromItemStack(stack).isNotEmpty() ||
               !(getFromItemStack(stack)?.baitEffects.isNullOrEmpty())
    }

    fun getFromItemStack(ItemStack stack): Seasoning? {
        val holder = stack.itemHolder
        return seasonings.find { it.ingredient.fits(holder) }
    }

    fun isSeasoning(ItemStack stack) = getFromItemStack(stack) != null
}
