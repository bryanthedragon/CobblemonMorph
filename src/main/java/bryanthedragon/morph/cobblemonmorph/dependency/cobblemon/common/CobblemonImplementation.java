/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  kotlin.Metadata
 *  kotlin.reflect.KClass
 *  net.minecraft.advancements.CriterionTrigger
 *  net.minecraft.commands.synchronization.ArgumentTypeInfo
 *  net.minecraft.commands.synchronization.ArgumentTypeInfo$Template
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.GameRules$Category
 *  net.minecraft.world.level.GameRules$Key
 *  net.minecraft.world.level.GameRules$Type
 *  net.minecraft.world.level.GameRules$Value
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;

import com.mojang.brigadier.arguments.ArgumentType;

import java.util.Collection;
import java.util.HashMap;

import kotlin.reflect.KClass;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CobblemonImplementation {
    @NotNull
    public ModAPI getModAPI();

    @NotNull
    public NetworkManager getNetworkManager();

    @NotNull
    public Environment environment();

    public boolean isModInstalled(@NotNull String var1);

    public void registerPermissionValidator();

    public void registerSoundEvents();

    public void registerItems();

    public void registerBlocks();

    public void registerEntityTypes();

    public void registerEntityAttributes();

    public void registerBlockEntityTypes();

    public void registerWorldGenFeatures();

    public void registerParticles();

    public void addFeatureToWorldGen(@NotNull ResourceKey<PlacedFeature> var1, @NotNull GenerationStep.Decoration var2, @Nullable TagKey<Biome> var3);

    public <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void registerCommandArgument(@NotNull ResourceLocation var1, @NotNull KClass<A> var2, @NotNull ArgumentTypeInfo<A, T> var3);

    @NotNull
    public <T extends GameRules.Value<T>> GameRules.Key<T> registerGameRule(@NotNull String var1, @NotNull GameRules.Category var2, @NotNull GameRules.Type<T> var3);

    @NotNull
    public <T extends CriterionTrigger<?>> T registerCriteria(@NotNull T var1);

    public void registerResourceReloader(@NotNull ResourceLocation var1, @NotNull PreparableReloadListener var2, @NotNull PackType var3, @NotNull Collection<? extends ResourceLocation> var4);

    @Nullable
    public MinecraftServer server();

    @NotNull
    public <T> HashMap<ResourceLocation, T> reloadJsonRegistry(@NotNull JsonDataRegistry<T> var1, @NotNull ResourceManager var2);

    public void registerCompostable(@NotNull ItemLike var1, float var2);

    public void registerBuiltinResourcePack(@NotNull ResourceLocation var1, @NotNull Component var2, @NotNull ResourcePackActivationBehaviour var3);
}

