/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.BerryRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import java.awt.Color

/**
 * The data registry for [Berry].
 *
 * @author Licious
 * @since November 28th, 2022
 */
public final class Berries : JsonDataRegistry<Berry> {

    override val ResourceLocation id = cobblemonResource("berries")
    override val type: PackType = PackType.SERVER_DATA
    override val observable = SimpleObservable<Berries>()

    override val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .registerTypeAdapter(MulchVariant.class, MulchVariantAdapter)
        .registerTypeAdapter(MinMaxBounds.Doubles.class, FloatNumberRangeAdapter)
        .registerTypeAdapter(Status.class, StatusAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(Collection.class, AABB.class).type, BoxCollectionAdapter)
        .registerTypeAdapter(AABB.class, BoxAdapter)
        .registerTypeAdapter(Vec3.class, VerboseVec3dAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(GrowthFactor.class, CobblemonGrowthFactorAdapter)
        .registerTypeAdapter(IntRange.class, VerboseIntRangeAdapter)
        .registerTypeAdapter(Color.class, LiteralHexColorAdapter)
        .registerTypeAdapter(Stat.class, CobblemonStatTypeAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(TagKey.class, Biome.class).type, TagKeyAdapter(Registries.BIOME))
        .registerTypeAdapter(BerrySpawnCondition.class, CobblemonBerrySpawnConditionAdapter)
        .create()
    override val typeToken: TypeToken<Berry> = TypeToken.get(Berry.class)
    override val resourcePath = "berries"

    private val berries = hashMapOf<ResourceLocation, Berry>()

    override fun reload(data: Map<ResourceLocation, Berry>) {
        this.berries.clear()
        data.forEach { (identifier, berry) ->
            try {
                berry.identifier = identifier
                berry.validate()
                this.berries[identifier] = berry
            } catch (Exception e) {
                Cobblemon.LOGGER.error("Skipped loading the {} berry", identifier, e)
            }
        }
        Cobblemon.LOGGER.info("Loaded {} berries", this.berries.size)
        this.observable.emit(this)
    }

    override fun sync(ServerPlayer player) {
        BerryRegistrySyncPacket(this.all()).sendToPlayer(player)
    }

    @JvmStatic
    fun all() = this.berries.values.toList()

    /**
     * Gets a berry if loaded.
     *
     * @param identifier The identifier of the berry.
     * @return The [Berry] if loaded otherwise null.
     */
    @JvmStatic
    fun getByIdentifier(ResourceLocation identifier): Berry? = this.berries[identifier]

    /**
     * Gets a berry if loaded.
     *
     * @param name The path of the identifier of the berry under the [Cobblemon.MODID] namespace.
     * @return The [Berry] if loaded otherwise null.
     */
    @JvmStatic
    fun getByName(String name): Berry? = this.getByIdentifier(cobblemonResource(name))

}