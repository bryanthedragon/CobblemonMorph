/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.def.PokedexDef
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.PokedexDexSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CodecBackedAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
public final class Dexes : JsonDataRegistry<PokedexDef> {
    override val id = cobblemonResource("dexes")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<Dexes>()

    override val Gson gson = GsonBuilder()
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(PokedexDef.class, CodecBackedAdapter(PokedexDef.CODEC))
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()

    override val typeToken: TypeToken<PokedexDef> = TypeToken.get(PokedexDef.class)
    override val resourcePath = "dexes"

    //Maps a dex id to its PokedexDef
    val dexEntryMap = linkedMapOf<ResourceLocation, PokedexDef>()

    override fun reload(data: Map<ResourceLocation, PokedexDef>) {
        dexEntryMap.clear()
        data.entries.sortedBy { it.value.sortOrder }.forEach { (id, def) -> dexEntryMap[id] = def }
        observable.emit(this)
    }

    override fun sync(ServerPlayer player) {
        PokedexDexSyncPacket(dexEntryMap.values).sendToPlayer(player)
    }
}