/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.PokeRodRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType

/**
 * The data registry for [PokeRod]s.
 * All the pokerod fields are guaranteed to exist
 */
public final class PokeRods : JsonDataRegistry<PokeRod> {

    override val id = cobblemonResource("pokerods")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<PokeRods>()

    // ToDo once datapack pokerod is implemented add required adapters here
    override val Gson gson = GsonBuilder()
        .disableHtmlEscaping()
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .setPrettyPrinting()
        .create()
    override val typeToken: TypeToken<PokeRod> = TypeToken.get(PokeRod.class)
    override val resourcePath = "pokerods"

    private val rods = mutableMapOf<ResourceLocation, PokeRod>()

    override fun reload(data: Map<ResourceLocation, PokeRod>) {
        data.forEach {
            it.value.name = it.key
            rods[it.key] = it.value
        }
        this.observable.emit(this)
    }

    override fun sync(ServerPlayer player) {
        PokeRodRegistrySyncPacket(rods.values).sendToPlayer(player)
    }

    /**
     * Gets a Pokerod from registry name.
     * @return the pokerod object if found otherwise null.
     */
    @JvmStatic
    fun getPokeRod(name : ResourceLocation): PokeRod? = rods[name]

}