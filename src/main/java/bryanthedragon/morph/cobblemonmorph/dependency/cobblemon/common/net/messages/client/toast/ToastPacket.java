/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import java.util.UUID;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ToastPacket(Component title, Component description, ItemStack icon, ResourceLocation frameTexture, Float progress, Int progressColor, UUID uuid, Behaviour behaviour) extends NetworkPacket<ToastPacket> {

    override ResourceLocation id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {buffer.writeText(this.title), buffer.writeText(this.description), buffer.writeItemStack(this.icon), buffer.writeIdentifier(this.frameTexture), buffer.writeFloat(this.progress), buffer.writeInt(this.progressColor), buffer.writeUUID(this.uuid), buffer.writeEnumConstant(this.behaviour)}

    final class Companion {

        ID = cobblemonResource("toast");

        ToastPacket decode(RegistryFriendlyByteBuf buffer) = ToastPacket(buffer.readText(), buffer.readText(), buffer.readItemStack(), buffer.readIdentifier(), buffer.readFloat(), buffer.readInt(), buffer.readUUID(), buffer.readEnumConstant(Behaviour.class))
    }

    enum Behaviour {SHOW_OR_UPDATE, HIDE}
}