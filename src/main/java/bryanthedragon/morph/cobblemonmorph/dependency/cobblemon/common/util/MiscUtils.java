/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PosableState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.ObtainableItem;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import kotlin.random.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector4f;

public class MiscUtils {
    ResourceLocation cobblemonResource(String path) { 
        ResourceLocation.fromNamespaceAndPath(Cobblemon.MODID, path)
    }
    fun cobblemonModel(String path, String variant) {
        ModelResourceLocation(cobblemonResource(path), variant);
    }
    String asTranslated() {
        Component.translatable(this);
    }
    String asResource() {
        ResourceLocation.parse(this);
    }
    String asTranslated(vararg data: Any) { 
        Component.translatable(this, *data);
    }
    String isInt() {
        this.toIntOrNull() != null;
    }
    String isDouble() {
        this.toDoubleOrNull() != null;
    }
    String isFloat() { 
        this.toFloatOrNull() != null
    }
    Boolean isHigherVersion(String other) {
        val thisSplits = split(".");
        val thatSplits = other.split(".");

        val thisCount = thisSplits.size;
        val thatCount = thatSplits.size;

        val min = min(thisCount, thatCount);
        for (i in 0 until min) {
            val thisDigit = thisSplits[i].toIntOrNull();
            val thatDigit = thatSplits[i].toIntOrNull();
            if (thisDigit == null || thatDigit == null) {
                return false;
            }
            if (thisDigit > thatDigit) {
                return true;
            } 
            else if (thisDigit < thatDigit) {
                return false;
            }
        }
        return thisCount > thatCount;
    }

    fun String.substitute(placeholder: String, value: Any?) = replace("{{$placeholder}}", value?.toString() ?: "")

    val Pair<Boolean, Boolean>.either: Boolean get() = first || second

    Float nextBetween(Random rand, Float min, Float max) {
        return nextFloat() * (max - min) + min;
    }

    Double nextBetween(Random rand, Double min, Double max) {
        return nextDouble() * (max - min) + min;
    }

    Int nextBetween(Random rand, Int min, Int max) {
        return nextInt(max - min + 1) + min;
    }

    infix fun <A, B> A.toDF(b: B) = com.mojang.datafixers.util.Pair(this, b);

    Boolean isUuid(String string) {
        return Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$").matches(string);
    }

    List<BlockPos> blockPositionsAsListRounded(VoxelShape shape) {
        val result = mutableListOf<BlockPos>();
        forAllBoxes { minX, minY, minZ, maxX, maxY, maxZ ->
            for (x in floor(minX).toInt() until ceil(maxX).toInt()) {
                for (y in floor(minY).toInt() until ceil(maxY).toInt()) {
                    for (z in floor(minZ).toInt() until ceil(maxZ).toInt()) {
                        result.add(BlockPos(x, y, z));
                    }
                }
            }
        }
        return result;
    }

    fun VoxelShape.blockPositionsAsList(): List<BlockPos> {
        val result = mutableListOf<BlockPos>();
        forAllBoxes { minX, minY, minZ, maxX, maxY, maxZ ->
            for (x in minX.toInt() until maxX.toInt()) {
                for (y in minY.toInt() until maxY.toInt()) {
                    for (z in minZ.toInt() until maxZ.toInt()) {
                        result.add(BlockPos(x, y, z));
                    }
                }
            }
        }
        return result;
    }

    operator fun <T> Consumer<T>.plus(action: (T) -> Unit): Consumer<T> {
        return andThen(action);
    }

    fun chainFutures(others: Iterator<() -> CompletableFuture<*>>, finalFuture: CompletableFuture<Unit>) {
        if (!others.hasNext()) {
            finalFuture.complete(Unit);
            return;
        }

        others.next().invoke().thenApply {
            chainFutures(others, finalFuture);
        }
    }

    val PosableState.isBattling: Boolean
        get() = (getEntity() as? PokemonEntity)?.isBattling == true || (getEntity() as? NPCEntity)?.isInBattle() == true;
    val PosableState.isUnderWater: Boolean
        get() = getEntity()?.isUnderWater == true;
    val PosableState.isInWater: Boolean
        get() = getEntity()?.isInWater == true;
    val PosableState.isInWaterOrRain: Boolean
        get() = getEntity()?.isInWaterOrRain == true;

    Boolean isInventoryKeyPressed(Screen screen Minecraft client?, Int keyCode, Int scanCode) {
        return client?.options?.keyInventory?.matches(keyCode, scanCode) == true;
    }

        EquipmentSlot toEquipmentSlot(InteractionHand hand) {
        return when (this) {
            hand.MAIN_HAND -> EquipmentSlot.MAINHAND;
            hand.OFF_HAND -> EquipmentSlot.OFFHAND;
        }
    }

    InteractionHand toHand(EquipmentSlot slot) {
        return when (this) {
            slot.MAINHAND -> InteractionHand.MAIN_HAND;
            slot.OFFHAND -> InteractionHand.OFF_HAND;
            else -> throw IllegalArgumentException("Invalid equipment slot: $this");
        }
    }

    val String.asUUID uuid?
        get() = try {
            UUID.fromString(this);
        }
        catch (Exception e) {
            null;
        }

    Int toHex(Float red, Float green, Float blue, Float alpha) {
        return ((alpha * 255).toInt() shl 24) or ((red * 255).toInt() shl 16) or ((green * 255).toInt() shl 8) or (blue * 255).toInt();
    }

    Vector4f toRGBA(Int integer) {
        val red = (this shr 16 and 255) / 255.0f;
        val green = (this shr 8 and 255) / 255.0f;
        val blue = (this and 255) / 255.0f;
        val alpha = (this shr 24 and 255) / 255.0f;
        return Vector4f(red, green, blue, alpha);
    }

    inline fun <reified T> Any.ifIsType(block: T.() -> Unit) {
        if (this is T) {
            block(this);
        }
    }

    // Maybe it'd be better to just Access Widen the nodes list.
    fun deleteNode(Path path, Int index) {
        val nodesAfterwards = mutableListOf<Node>();
        for (i in index + 1 until this.nodeCount) {
            nodesAfterwards.add(this.getNode(i));
        }
        val nodesBefore = mutableListOf<Node>();
        for (i in 0 until index) {
            nodesBefore.add(this.getNode(i));
        }
        this.truncateNodes(nodesBefore.size + nodesAfterwards.size);
        var i = 0;
        val correctNodes = nodesBefore + nodesAfterwards;
        for (node in correctNodes) {
            this.replaceNode(i++, node);
        }
    }

    ObtainableItem findMatchingEntry(Collection<ObtainableItem> collection, RegistryAccess registryAccess, ItemStack stack) ? {
        return if (stack.isEmpty) {
            null;
        } 
        else {
            this.find { it.item?.isItemObtainable(registryAccess, stack) != false }
        }
    }

    /**
     * Resolves entity types from an entity type id or entity type tag.
     *
     * @param registryAccess The registry access to use for resolving entity types.
     * @param entityType A literal entity ID or an entity tag.
     */
    Set<EntityType<*>> resolveEntityTypes(RegistryAccess registryAccess, String entityType) {
        val registry = registryAccess.registry(Registries.ENTITY_TYPE).getOrNull() ?: return emptySet()
        val types = mutableSetOf<EntityType<*>>()

        if (entityType.startsWith("#")) {
            val id = ResourceLocation.tryParse(entityType.substring(1)) ?: return emptySet()
            val tag = TagKey.create(Registries.ENTITY_TYPE, id)
            registry.getTag(tag).getOrNull()?.stream()?.forEach { types.add(it.value()) }
        } 
        else {
            val id = ResourceLocation.tryParse(entityType) ?: return emptySet()
            registry.get(id)?.let { types.add(it) }
        }
        return types;
    }
}