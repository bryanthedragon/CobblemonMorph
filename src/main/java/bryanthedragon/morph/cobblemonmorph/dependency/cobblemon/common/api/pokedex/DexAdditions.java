/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.DexAdditions.DexAddition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.def.AggregatePokedexDef
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.def.SimplePokedexDef
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.entry.DexEntries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
public final class DexAdditions : JsonDataRegistry<DexAddition> {
    override val id = cobblemonResource("dex_additions")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<DexAdditions>()

    override val Gson gson = GsonBuilder()
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()

    override val typeToken: TypeToken<DexAddition> = TypeToken.get(DexAddition.class)
    override val resourcePath = "dex_additions"

    override fun reload(data: Map<ResourceLocation, DexAddition>) {
        data.entries.forEach { (key, value) ->
            Dexes.dexEntryMap[value.dexId]?.let { dexDef ->
                if (dexDef is SimplePokedexDef) {
                    var invalid = false
                    value.entries.forEach {
                        if (!DexEntries.entries.containsKey(it)) {
                            invalid = true
                            Cobblemon.LOGGER.error("Unable to apply dex addition {} as the entry {} does not exist", key, it)
                        }
                    }
                    if (invalid) {
                        return@forEach
                    }
                    dexDef.appendEntries(value.entries)
                } else if (dexDef is AggregatePokedexDef) {
                    var invalid = false
                    value.entries.forEach {
                        if (!Dexes.dexEntryMap.containsKey(it)) {
                            invalid = true
                            Cobblemon.LOGGER.error("Unable to apply dex addition {} as the sub-dex {} does not exist", key, it)
                        }
                    }
                    dexDef.appendSubDexIds(value.entries)
                }
            } ?: Cobblemon.LOGGER.error("Unable to apply dex addition {} as the dex {} does not exist", key, value.dexId)
        }
        observable.emit(this)
    }

    override fun sync(ServerPlayer player) {} // Will be synced as part of the dexes

    class DexAddition {
        val dexResourceLocation id = ResourceLocation.parse("cobblemon:national")
        /** Could be PokedexEntry locations or PokedexDef locations depending on what type dex the dexId points to. */
        val entries = mutableListOf<ResourceLocation>()
    }
}