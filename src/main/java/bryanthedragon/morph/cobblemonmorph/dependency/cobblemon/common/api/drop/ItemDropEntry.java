/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3Extensions;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

/**
 * A drop that is an actual item.
 *
 * @author Hiroku
 * @since July 24th, 2022
 */
public open class ItemDropEntry : DropEntry {
    var percentage = 100F
    var quantity = 1
    open var quantityIntRange range? = null
    var maxSelectableTimes = 1
    open val dropMethod: ItemDropMethod? = null
    open var item = ResourceLocation.parse("minecraft:fish")
    open val components: DataComponentMap? = null

    fun drop(LivingEntity entity?, ServerLevel world, Vec3 pos, ServerPlayer player?) {
        val item = world.registryAccess().registryOrThrow(Registries.ITEM).get(item) ?: return LOGGER.error("Unable to load drop item: $item")
        val stack = ItemStack(item, quantityRange?.random() ?: quantity)
        val inLava = world.getBlockState(pos.toBlockPos()).block == Blocks.LAVA
        val dropMethod = (dropMethod ?: Cobblemon.config.defaultDropItemMethod).let {
            if (inLava) {
                ItemDropMethod.TO_INVENTORY
            }
            else {
                it
            }
        }
        val builder = DataComponentPatch.builder()
        components?.forEach {
            builder.set(it)
        }
        stack.applyComponentsAndValidate(builder.build())

        if (dropMethod == ItemDropMethod.ON_PLAYER && player != null) {
            world.addFreshEntity(ItemEntity(player.level(), player.x, player.y, player.z, stack))
        }
        else if (dropMethod == ItemDropMethod.TO_INVENTORY && player != null && !stack.isEmpty) {
            val name = stack.hoverName
            val count = stack.count
            val succeeded = player.addItem(stack)
            if (Cobblemon.config.announceDropItems) {
                player.sendSystemMessage(
                    if (succeeded) lang("drop.item.inventory", count, name.copy().green())
                    else lang("drop.item.full", name).red()
                )
            }
        }
        else if (dropMethod == ItemDropMethod.ON_ENTITY && entity != null) {
            world.addFreshEntity(ItemEntity(entity.level(), entity.x, entity.y, entity.z, stack))
        }
        else {
            world.addFreshEntity(ItemEntity(world, pos.x, pos.y, pos.z, stack))
        }
    }

    fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeFloat(this.percentage)
        buffer.writeVarInt(this.quantity)
        buffer.writeResourceLocation(this.item)
        buffer.writeNullable(this.quantityRange) { _, it -> buffer.writeVarInt(it.first); buffer.writeVarInt(it.last) }
    }

    ItemDropEntry decode(RegistryFriendlyByteBuf buffer) {
        this.percentage = buffer.readFloat()
        this.quantity = buffer.readVarInt()
        this.item = buffer.readResourceLocation()
        this.quantityRange = buffer.readNullable { buffer.readVarInt()..buffer.readVarInt() }
        return this
    }
}