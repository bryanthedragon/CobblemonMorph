/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter;

import java.util.List;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.RenderableStarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class OpenStarterUIPacket internal constructor(List<RenderableStarterCategory> categories) : NetworkPacket<OpenStarterUIPacket> {

    val id = ID;

    constructor(categories: Collection<StarterCategory>) : this(categories.map { it.asRenderableStarterCategory() })

    fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(categories.size);
        categories.forEach {
            buffer.writeString(it.name);
            buffer.writeString(it.displayName);
            buffer.writeInt(it.pokemon.size);
            it.pokemon.forEach { it.saveToBuffer(buffer) }
        }
    }

    final class Companion {
        val ID = cobblemonResource("open_starter");
        fun decode(RegistryFriendlyByteBuf buffer): OpenStarterUIPacket {
            val numCategories = buffer.readInt();
            val categories = arrayListOf<RenderableStarterCategory>();
            for (i in 0 until numCategories) {
                val name = buffer.readString();
                val displayName = buffer.readString();
                val numProperties = buffer.readInt();
                val renderablePokemon = mutableListOf<RenderablePokemon>();
                repeat(times = numProperties) {
                    renderablePokemon.add(RenderablePokemon.loadFromBuffer(buffer));
                }
                categories.add(RenderableStarterCategory(name = name, displayName = displayName, pokemon = renderablePokemon);
                )
            }
            return OpenStarterUIPacket(categories);
        }
    }
}
