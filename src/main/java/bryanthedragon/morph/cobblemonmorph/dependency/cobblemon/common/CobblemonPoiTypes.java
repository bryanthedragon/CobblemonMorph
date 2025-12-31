/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;

import com.google.common.collect.ImmutableSet;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public final class CobblemonPoiTypes extends PlatformRegistry<Registry<PoiType>, ResourceKey<Registry<PoiType>>, PoiType>() {
    @SuppressWarnings("deprecation")
    val Registry<PoiType> registry = BuiltInRegistries.POINT_OF_INTEREST_TYPE;

    val ResourceKey<Registry<PoiType>> resourceKey = Registries.POINT_OF_INTEREST_TYPE;

    val NURSE_KEY = createKey("nurse", CobblemonBlocks.HEALING_MACHINE, 1, 1);

    val SACCHARINE_LOG_SLATHERED_KEY = createKey("saccharine_log_slathered", CobblemonBlocks.SACCHARINE_LOG_SLATHERED, 0, 1);

    val INCENSE_SWEET_KEY = createKey("incense_sweet", CobblemonBlocks.INCENSE_SWEET, 0, 1);

    private ResourceKey<PoiType> createKey(String string, Block block, Int maxTickets, Int validRange) = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, cobblemonResource(string)).also { resourceKey -> create(resourceKey.location().path, PoiType(getBlockStates(block), maxTickets, validRange))}

    private Set<BlockState> getBlockStates(Block block) = ImmutableSet.copyOf(block.stateDefinition.possibleStates)
}
