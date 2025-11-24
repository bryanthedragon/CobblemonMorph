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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Environment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ModAPI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ResourcePackActivationBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.Collection;
import java.util.HashMap;
import kotlin.Metadata;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00e0\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J5\u0010\u000b\u001a\u00020\n2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007H&\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH&\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H&\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\nH&\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\nH&\u00a2\u0006\u0004\b\u0017\u0010\u0016J'\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001bH&\u00a2\u0006\u0004\b\u001d\u0010\u001eJW\u0010(\u001a\u00020\n\"\f\b\u0000\u0010 *\u0006\u0012\u0002\b\u00030\u001f\"\u000e\b\u0001\u0010\"*\b\u0012\u0004\u0012\u00028\u00000!2\u0006\u0010#\u001a\u00020\u00182\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000$2\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010&H&\u00a2\u0006\u0004\b(\u0010)J\u001f\u0010.\u001a\u00020\n2\u0006\u0010+\u001a\u00020*2\u0006\u0010-\u001a\u00020,H&\u00a2\u0006\u0004\b.\u0010/J%\u00102\u001a\u00028\u0000\"\f\b\u0000\u0010\"*\u0006\u0012\u0002\b\u0003002\u0006\u00101\u001a\u00028\u0000H&\u00a2\u0006\u0004\b2\u00103J\u000f\u00104\u001a\u00020\nH&\u00a2\u0006\u0004\b4\u0010\u0016J\u000f\u00105\u001a\u00020\nH&\u00a2\u0006\u0004\b5\u0010\u0016JC\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000<\"\u000e\b\u0000\u0010\"*\b\u0012\u0004\u0012\u00028\u0000062\u0006\u00107\u001a\u00020\u00102\u0006\u00109\u001a\u0002082\f\u0010;\u001a\b\u0012\u0004\u0012\u00028\u00000:H&\u00a2\u0006\u0004\b=\u0010>J\u000f\u0010?\u001a\u00020\nH&\u00a2\u0006\u0004\b?\u0010\u0016J\u000f\u0010@\u001a\u00020\nH&\u00a2\u0006\u0004\b@\u0010\u0016J\u000f\u0010A\u001a\u00020\nH&\u00a2\u0006\u0004\bA\u0010\u0016J5\u0010G\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u00182\u0006\u0010C\u001a\u00020B2\u0006\u0010;\u001a\u00020D2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00180EH&\u00a2\u0006\u0004\bG\u0010HJ\u000f\u0010I\u001a\u00020\nH&\u00a2\u0006\u0004\bI\u0010\u0016J\u000f\u0010J\u001a\u00020\nH&\u00a2\u0006\u0004\bJ\u0010\u0016JG\u0010Q\u001a\u001e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00028\u00000Oj\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00028\u0000`P\"\u0004\b\u0000\u0010\"2\f\u0010L\u001a\b\u0012\u0004\u0012\u00028\u00000K2\u0006\u0010N\u001a\u00020MH&\u00a2\u0006\u0004\bQ\u0010RJ\u0011\u0010T\u001a\u0004\u0018\u00010SH&\u00a2\u0006\u0004\bT\u0010UR\u0014\u0010Y\u001a\u00020V8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bW\u0010XR\u0014\u0010]\u001a\u00020Z8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b[\u0010\\\u00a8\u0006^"}, d2={"Lcom/cobblemon/mod/common/CobblemonImplementation;", "", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;", "feature", "Lnet/minecraft/world/gen/GenerationStep$Feature;", "step", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "validTag", "", "addFeatureToWorldGen", "(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/levelgen/GenerationStep$Decoration;Lnet/minecraft/tags/TagKey;)V", "Lcom/cobblemon/mod/common/Environment;", "environment", "()Lcom/cobblemon/mod/common/Environment;", "", "id", "", "isModInstalled", "(Ljava/lang/String;)Z", "registerBlockEntityTypes", "()V", "registerBlocks", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/chat/Component;", "title", "Lcom/cobblemon/mod/common/ResourcePackActivationBehaviour;", "activationBehaviour", "registerBuiltinResourcePack", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/Component;Lcom/cobblemon/mod/common/ResourcePackActivationBehaviour;)V", "Lcom/mojang/brigadier/arguments/ArgumentType;", "A", "Lnet/minecraft/command/argument/serialize/ArgumentSerializer$ArgumentTypeProperties;", "T", "identifier", "Lkotlin/reflect/KClass;", "argumentClass", "Lnet/minecraft/commands/synchronization/ArgumentTypeInfo;", "serializer", "registerCommandArgument", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/reflect/KClass;Lnet/minecraft/commands/synchronization/ArgumentTypeInfo;)V", "Lnet/minecraft/world/level/ItemLike;", "item", "", "chance", "registerCompostable", "(Lnet/minecraft/world/level/ItemLike;F)V", "Lnet/minecraft/advancements/CriterionTrigger;", "criteria", "registerCriteria", "(Lnet/minecraft/advancements/CriterionTrigger;)Lnet/minecraft/advancements/CriterionTrigger;", "registerEntityAttributes", "registerEntityTypes", "Lnet/minecraft/world/GameRules$Rule;", "name", "Lnet/minecraft/world/GameRules$Category;", "category", "Lnet/minecraft/world/GameRules$Type;", "type", "Lnet/minecraft/world/GameRules$Key;", "registerGameRule", "(Ljava/lang/String;Lnet/minecraft/world/level/GameRules$Category;Lnet/minecraft/world/level/GameRules$Type;)Lnet/minecraft/world/level/GameRules$Key;", "registerItems", "registerParticles", "registerPermissionValidator", "Lnet/minecraft/server/packs/resources/PreparableReloadListener;", "reloader", "Lnet/minecraft/server/packs/PackType;", "", "dependencies", "registerResourceReloader", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/packs/resources/PreparableReloadListener;Lnet/minecraft/server/packs/PackType;Ljava/util/Collection;)V", "registerSoundEvents", "registerWorldGenFeatures", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "registry", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "reloadJsonRegistry", "(Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;Lnet/minecraft/server/packs/resources/ResourceManager;)Ljava/util/HashMap;", "Lnet/minecraft/server/MinecraftServer;", "server", "()Lnet/minecraft/server/MinecraftServer;", "Lcom/cobblemon/mod/common/ModAPI;", "getModAPI", "()Lcom/cobblemon/mod/common/ModAPI;", "modAPI", "Lcom/cobblemon/mod/common/NetworkManager;", "getNetworkManager", "()Lcom/cobblemon/mod/common/NetworkManager;", "networkManager", "common"})
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

