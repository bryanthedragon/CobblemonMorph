/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record UpdatePastureConflictFlagPacket(UUID pokemonId, Boolean enabled) implements NetworkPacket<UpdatePastureConflictFlagPacket> {
    ID id;

    final class Companion {
        ID = MiscUtils.cobblemonResource("update_pasture_conflict_flag");

        UpdatePastureConflictFlagPacket decode(RegistryFriendlyByteBuf buf) {
            return UpdatePastureConflictFlagPacket(buf.readUUID(), buf.readBoolean())
        }
    }

    fun encode(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(pokemonId);
        buf.writeBoolean(enabled);
    }

    @Override
    public @NotNull ResourceLocation getId() {
    }
}