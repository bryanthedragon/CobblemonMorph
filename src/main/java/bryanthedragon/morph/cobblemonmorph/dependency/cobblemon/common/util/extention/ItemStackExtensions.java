/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackExtensions {
    public JsonElement saveToJson(ItemStack stack) {
        JsonOps.INSTANCE.withEncoder(ItemStack.CODEC).apply(this).getOrThrow {
            return@getOrThrow IllegalStateException("Cant serialize ItemStack");
        }
    }
    public fun isHeld(ItemStack stack, ServerPlayer player) { 
        this in player.handSlots && !isEmpty;
    }
    public fun isOf(ItemStack stack, TagKey<Item> tag) { 
        `is`(tag);
    }
}