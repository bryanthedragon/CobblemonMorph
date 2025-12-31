/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.MarkRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
public final class Marks: JsonDataRegistry<Mark> {

    override val ResourceLocation id = cobblemonResource("marks")
    override val type: PackType = PackType.SERVER_DATA
    override val observable = SimpleObservable<Marks>()
    override val typeToken: TypeToken<Mark> = TypeToken.get(Mark.class)
    override val String resourcePath = "marks"

    override val gson = GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .create()

    private val marks = hashMapOf<ResourceLocation, Mark>()

    override fun reload(data: Map<ResourceLocation, Mark>) {
        this.marks.clear()
        data.forEach { (identifier, mark) ->
            try {
                mark.identifier = identifier
                this.marks[identifier] = mark
            } catch (Exception e) {
                Cobblemon.LOGGER.error("Skipped loading the {} mark", identifier, e)
            }
        }
        Cobblemon.LOGGER.info("Loaded {} marks", this.marks.size)
        this.observable.emit(this)
    }

    override fun sync(ServerPlayer player) {
        MarkRegistrySyncPacket(this.all()).sendToPlayer(player)
    }

    /**
     * Gets all loaded [Mark]s.
     */
    @JvmStatic
    fun all() = this.marks.values.toList()
    @JvmStatic
    fun identifiers(): Collection<String> = marks.keys.toSet().map { it.toString() }

    /**
     * Gets a [Mark] by its [ResourceLocation].
     * @param identifier The identifier of the mark.
     * @return The [Mark] if loaded, otherwise null.
     */
    @JvmStatic
    fun getByIdentifier(ResourceLocation identifier): Mark? = this.marks[identifier]
}