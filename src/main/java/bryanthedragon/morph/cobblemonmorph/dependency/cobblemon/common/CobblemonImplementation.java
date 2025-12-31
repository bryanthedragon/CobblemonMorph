/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mod.CobblemonModAPI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.network.CobblemonNetworkManager;

import com.mojang.brigadier.arguments.ArgumentType;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Collection;

public interface CobblemonImplementation {

    CobblemonModAPI getModAPI();

    /**
     * 
     */
    CobblemonNetworkManager getNetworkManager();

    /**
     * TODO
     *
     * @return The current environment.
     */
    CobblemonEnvironment environment();

    /**
     * TODO
     *
     * @param id The mod ID to check.
     * @return True if the mod is installed, false otherwise.
     */
    boolean isModInstalled(String id);

    /**
     * TODO
     */
    void registerPermissionValidator();

    /**
     * TODO
     */
    void registerSoundEvents();

    void registerDataComponents();

    void registerEntityDataSerializers();

    /**
     * TODO
     */
    void registerItems();

    /**
     * TODO
     */
    void registerBlocks();

    /**
     * TODO
     */
    void registerEntityTypes();

    /**
     * TODO
     */
    void registerEntityAttributes();

    /**
     * TODO
     */
    void registerBlockEntityTypes();

    void registerPoiTypes();

    /**
     * TODO
     */
    void registerVillagers();

    void registerRecipeSerializers();

    void registerRecipeTypes();

    /**
     * TODO
     */
    void registerWorldGenFeatures();

    void registerParticles();

    void registerMenu();

    void registerEntitySubPredicates();

    /**
     * Add a feature to the current platform implementation.
     *
     * @param feature  The [PlacedFeature] being added.
     * @param step     The [GenerationStep.Decoration] of this feature.
     * @param validTag The [TagKey] required by the [Biome] for this feature to generate in, if null all biomes are valid.
     */
    void addFeatureToWorldGen(ResourceKey<PlacedFeature> feature, GenerationStep.Decoration step, TagKey<Biome> validTag);

    /**
     * TODO
     *
     * @param identifier    The resource location identifier.
     * @param argumentClass The class of the argument type.
     * @param serializer    The argument type info serializer.
     * @param <A>           The argument type.
     * @param <T>           The template type.
     */
    <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void registerCommandArgument(ResourceLocation identifier, Class<A> argumentClass, ArgumentTypeInfo<A, T> serializer);

    /**
     * TODO
     *
     * @param name     The name of the game rule.
     * @param category The category of the game rule.
     * @param type     The type of the game rule.
     * @param <T>      The game rule value type.
     * @return The registered game rule key.
     */
    <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(String name, GameRules.Category category, GameRules.Type<T> type);

    /**
     * TODO
     */
    void registerCriteria();

    /**
     * TODO
     *
     * @param identifier   The resource location identifier.
     * @param reloader     The reload listener.
     * @param type         The pack type.
     * @param dependencies The collection of dependencies.
     */
    void registerResourceReloader(ResourceLocation identifier, PreparableReloadListener reloader, PackType type, Collection<ResourceLocation> dependencies);

    /**
     * TODO
     *
     * @return The Minecraft server instance, or null if not available.
     */
    MinecraftServer server();

    /**
     * Registers an item to the [ComposterBlock].
     *
     * @param item   The [ItemLike] being registered.
     * @param chance The chance % of increasing the composter level, 0 to 1 expected.
     */
    void registerCompostable(ItemLike item, float chance);
}

public enum ResourcePackActivationBehaviour {
    /**
     * The resource pack will start disabled.
     */
    NORMAL,

    /**
     * The resource pack will start enabled.
     */
    DEFAULT_ENABLED,

    /**
     * The resource pack will always be enabled.
     * The user can reorder it but cannot remove it.
     */
    ALWAYS_ENABLED
}