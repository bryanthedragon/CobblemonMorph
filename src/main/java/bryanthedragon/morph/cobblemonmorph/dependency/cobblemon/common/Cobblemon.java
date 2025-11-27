/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.ReplaceWith
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.MutablePropertyReference1
 *  kotlin.jvm.internal.MutablePropertyReference1Impl
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.properties.Delegates
 *  kotlin.properties.ObservableProperty
 *  kotlin.properties.ReadWriteProperty
 *  kotlin.ranges.RangesKt
 *  kotlin.reflect.KCallable
 *  kotlin.reflect.KClass
 *  kotlin.reflect.KProperty
 *  kotlin.reflect.KProperty1
 *  kotlin.reflect.full.KClasses
 *  kotlin.reflect.jvm.KCallablesJvm
 *  kotlin.reflect.jvm.ReflectJvmMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.commands.synchronization.ArgumentTypeInfo
 *  net.minecraft.commands.synchronization.SingletonArgumentInfo
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonImplementation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.SeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.CommandDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FriendshipUpdatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.IdentifierDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.PoseTypeDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.StringSetDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.Vec3DataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculators;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffectRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.StandardExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.ChoiceSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.EvCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Generation8EvCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.BestSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaContextResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter.StarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang.NbtMoLangDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownThread;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.DialogueArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.MoveArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonStoreArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.SpawnBucketArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.CobblemonConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.LastChangedVersion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.constraint.IntConstraint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.AdvancementHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.permission.LaxPermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects.PokemonAspectsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.TagSeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonHeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.HiddenAbilityPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.UncatchableProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags.PokemonFlagProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.stat.CobblemonStatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.starter.CobblemonStarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerInventoryExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.StringExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.CobblemonPlacedFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore.CobblemonOrePlacedFeatures;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.KCallablesJvm;
import kotlin.reflect.jvm.ReflectJvmMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.NonNullList;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00cc\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\n\b\u0002\u00a2\u0006\u0005\b\u009f\u0001\u0010\tJ\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\tJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u000f\u0010\u0012\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u0012\u0010\tJ\r\u0010\u0013\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0013\u0010\tR\u0014\u0010\u0015\u001a\u00020\u00148\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00148\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u0016R\u0014\u0010\u001d\u001a\u00020\u00148\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u0016R\"\u0010\u001f\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0017\u0010&\u001a\u00020%8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u0017\u0010+\u001a\u00020*8\u0006\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R*\u00106\u001a\u00020/2\u0006\u00100\u001a\u00020/8F@FX\u0087\u000e\u00a2\u0006\u0012\u0012\u0004\b5\u0010\t\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\"\u00108\u001a\u0002078\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u00109\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u0017\u0010?\u001a\u00020>8\u0006\u00a2\u0006\f\n\u0004\b?\u0010@\u001a\u0004\bA\u0010BR\"\u0010D\u001a\u00020C8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010E\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\"\u0010K\u001a\u00020J8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bK\u0010L\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\"\u0010\u000f\u001a\u00020\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000f\u0010Q\u001a\u0004\bR\u0010S\"\u0004\bT\u0010\u0011R\"\u0010V\u001a\u00020U8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bV\u0010W\u001a\u0004\bV\u0010X\"\u0004\bY\u0010ZR\"\u0010\\\u001a\u00020[8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\\\u0010]\u001a\u0004\b^\u0010_\"\u0004\b`\u0010aR+\u0010j\u001a\u00020b2\u0006\u0010c\u001a\u00020b8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\bd\u0010e\u001a\u0004\bf\u0010g\"\u0004\bh\u0010iR\"\u0010l\u001a\u00020k8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bl\u0010m\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR\"\u0010s\u001a\u00020r8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bs\u0010t\u001a\u0004\bu\u0010v\"\u0004\bw\u0010xR\"\u0010z\u001a\u00020y8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bz\u0010{\u001a\u0004\b|\u0010}\"\u0004\b~\u0010\u007fR\u001d\u0010\u0081\u0001\u001a\u00030\u0080\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0081\u0001\u0010\u0082\u0001\u001a\u0006\b\u0083\u0001\u0010\u0084\u0001R(\u0010\u0085\u0001\u001a\u00020\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0017\n\u0006\b\u0085\u0001\u0010\u0086\u0001\u001a\u0005\b\u0087\u0001\u0010\r\"\u0006\b\u0088\u0001\u0010\u0089\u0001R*\u0010\u008b\u0001\u001a\u00030\u008a\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u008b\u0001\u0010\u008c\u0001\u001a\u0006\b\u008d\u0001\u0010\u008e\u0001\"\u0006\b\u008f\u0001\u0010\u0090\u0001R*\u0010\u0092\u0001\u001a\u00030\u0091\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0092\u0001\u0010\u0093\u0001\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0006\b\u0096\u0001\u0010\u0097\u0001R*\u0010\u0099\u0001\u001a\u00030\u0098\u00018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0018\n\u0006\b\u0099\u0001\u0010\u009a\u0001\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001\"\u0006\b\u009d\u0001\u0010\u009e\u0001\u00a8\u0006\u00a0\u0001"}, d2={"Lcom/cobblemon/mod/common/Cobblemon;", "", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/Level;", "dimension", "getLevel", "(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/world/level/Level;", "", "initialize", "()V", "loadConfig", "Lcom/cobblemon/mod/common/config/starter/StarterConfig;", "loadStarterConfig", "()Lcom/cobblemon/mod/common/config/starter/StarterConfig;", "Lcom/cobblemon/mod/common/CobblemonImplementation;", "implementation", "preInitialize", "(Lcom/cobblemon/mod/common/CobblemonImplementation;)V", "registerArgumentTypes", "saveConfig", "", "CONFIG_PATH", "Ljava/lang/String;", "Lorg/apache/logging/log4j/Logger;", "LOGGER", "Lorg/apache/logging/log4j/Logger;", "getLOGGER", "()Lorg/apache/logging/log4j/Logger;", "MODID", "VERSION", "Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "areaContextResolver", "Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "getAreaContextResolver", "()Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "setAreaContextResolver", "(Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;)V", "Lcom/cobblemon/mod/common/battles/BattleRegistry;", "battleRegistry", "Lcom/cobblemon/mod/common/battles/BattleRegistry;", "getBattleRegistry", "()Lcom/cobblemon/mod/common/battles/BattleRegistry;", "Lcom/cobblemon/mod/common/api/spawning/BestSpawner;", "bestSpawner", "Lcom/cobblemon/mod/common/api/spawning/BestSpawner;", "getBestSpawner", "()Lcom/cobblemon/mod/common/api/spawning/BestSpawner;", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "value", "getCaptureCalculator", "()Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "setCaptureCalculator", "(Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;)V", "getCaptureCalculator$annotations", "captureCalculator", "Lcom/cobblemon/mod/common/config/CobblemonConfig;", "config", "Lcom/cobblemon/mod/common/config/CobblemonConfig;", "getConfig", "()Lcom/cobblemon/mod/common/config/CobblemonConfig;", "setConfig", "(Lcom/cobblemon/mod/common/config/CobblemonConfig;)V", "Lcom/cobblemon/mod/common/api/data/DataProvider;", "dataProvider", "Lcom/cobblemon/mod/common/api/data/DataProvider;", "getDataProvider", "()Lcom/cobblemon/mod/common/api/data/DataProvider;", "Lcom/cobblemon/mod/common/api/pokemon/stats/EvCalculator;", "evYieldCalculator", "Lcom/cobblemon/mod/common/api/pokemon/stats/EvCalculator;", "getEvYieldCalculator", "()Lcom/cobblemon/mod/common/api/pokemon/stats/EvCalculator;", "setEvYieldCalculator", "(Lcom/cobblemon/mod/common/api/pokemon/stats/EvCalculator;)V", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceCalculator;", "experienceCalculator", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceCalculator;", "getExperienceCalculator", "()Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceCalculator;", "setExperienceCalculator", "(Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceCalculator;)V", "Lcom/cobblemon/mod/common/CobblemonImplementation;", "getImplementation", "()Lcom/cobblemon/mod/common/CobblemonImplementation;", "setImplementation", "", "isDedicatedServer", "Z", "()Z", "setDedicatedServer", "(Z)V", "Lcom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory;", "molangData", "Lcom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory;", "getMolangData", "()Lcom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory;", "setMolangData", "(Lcom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory;)V", "Lcom/cobblemon/mod/common/api/permission/PermissionValidator;", "<set-?>", "permissionValidator$delegate", "Lkotlin/properties/ReadWriteProperty;", "getPermissionValidator", "()Lcom/cobblemon/mod/common/api/permission/PermissionValidator;", "setPermissionValidator", "(Lcom/cobblemon/mod/common/api/permission/PermissionValidator;)V", "permissionValidator", "Lcom/cobblemon/mod/common/api/storage/player/PlayerDataStoreManager;", "playerData", "Lcom/cobblemon/mod/common/api/storage/player/PlayerDataStoreManager;", "getPlayerData", "()Lcom/cobblemon/mod/common/api/storage/player/PlayerDataStoreManager;", "setPlayerData", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerDataStoreManager;)V", "Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "prospector", "Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "getProspector", "()Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "setProspector", "(Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;)V", "Lcom/cobblemon/mod/common/api/SeasonResolver;", "seasonResolver", "Lcom/cobblemon/mod/common/api/SeasonResolver;", "getSeasonResolver", "()Lcom/cobblemon/mod/common/api/SeasonResolver;", "setSeasonResolver", "(Lcom/cobblemon/mod/common/api/SeasonResolver;)V", "Lcom/cobblemon/mod/common/battles/ShowdownThread;", "showdownThread", "Lcom/cobblemon/mod/common/battles/ShowdownThread;", "getShowdownThread", "()Lcom/cobblemon/mod/common/battles/ShowdownThread;", "starterConfig", "Lcom/cobblemon/mod/common/config/starter/StarterConfig;", "getStarterConfig", "setStarterConfig", "(Lcom/cobblemon/mod/common/config/starter/StarterConfig;)V", "Lcom/cobblemon/mod/common/api/starter/StarterHandler;", "starterHandler", "Lcom/cobblemon/mod/common/api/starter/StarterHandler;", "getStarterHandler", "()Lcom/cobblemon/mod/common/api/starter/StarterHandler;", "setStarterHandler", "(Lcom/cobblemon/mod/common/api/starter/StarterHandler;)V", "Lcom/cobblemon/mod/common/api/pokemon/stats/StatProvider;", "statProvider", "Lcom/cobblemon/mod/common/api/pokemon/stats/StatProvider;", "getStatProvider", "()Lcom/cobblemon/mod/common/api/pokemon/stats/StatProvider;", "setStatProvider", "(Lcom/cobblemon/mod/common/api/pokemon/stats/StatProvider;)V", "Lcom/cobblemon/mod/common/api/storage/PokemonStoreManager;", "storage", "Lcom/cobblemon/mod/common/api/storage/PokemonStoreManager;", "getStorage", "()Lcom/cobblemon/mod/common/api/storage/PokemonStoreManager;", "setStorage", "(Lcom/cobblemon/mod/common/api/storage/PokemonStoreManager;)V", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Cobblemon.kt\ncom/cobblemon/mod/common/Cobblemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 Delegates.kt\nkotlin/properties/Delegates\n*L\n1#1,525:1\n1855#2:526\n1856#2:529\n13579#3,2:527\n1#4:530\n33#5,3:531\n*S KotlinDebug\n*F\n+ 1 Cobblemon.kt\ncom/cobblemon/mod/common/Cobblemon\n*L\n447#1:526\n447#1:529\n450#1:527,2\n170#1:531,3\n*E\n"})
public final class Cobblemon {
    @NotNull
    public static final Cobblemon INSTANCE;
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    @NotNull
    public static final String MODID = "cobblemon";
    @NotNull
    public static final String VERSION = "1.5.2";
    @NotNull
    public static final String CONFIG_PATH = "config/cobblemon/main.json";
    @NotNull
    private static final Logger LOGGER;
    public static CobblemonImplementation implementation;
    @NotNull
    private static ExperienceCalculator experienceCalculator;
    @NotNull
    private static EvCalculator evYieldCalculator;
    @NotNull
    private static StarterHandler starterHandler;
    private static boolean isDedicatedServer;
    @NotNull
    private static final ShowdownThread showdownThread;
    public static CobblemonConfig config;
    @NotNull
    private static SpawningProspector prospector;
    @NotNull
    private static AreaContextResolver areaContextResolver;
    @NotNull
    private static final BestSpawner bestSpawner;
    @NotNull
    private static final BattleRegistry battleRegistry;
    @NotNull
    private static PokemonStoreManager storage;
    @NotNull
    private static NbtMoLangDataStoreFactory molangData;
    public static PlayerDataStoreManager playerData;
    public static StarterConfig starterConfig;
    @NotNull
    private static final DataProvider dataProvider;
    @NotNull
    private static final ReadWriteProperty permissionValidator$delegate;
    @NotNull
    private static StatProvider statProvider;
    @NotNull
    private static SeasonResolver seasonResolver;

    private Cobblemon() {
    }

    @NotNull
    public final Logger getLOGGER() {
        return LOGGER;
    }

    @NotNull
    public final CobblemonImplementation getImplementation() {
        CobblemonImplementation cobblemonImplementation = implementation;
        if (cobblemonImplementation != null) {
            return cobblemonImplementation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"implementation");
        return null;
    }

    public final void setImplementation(@NotNull CobblemonImplementation cobblemonImplementation) {
        Intrinsics.checkNotNullParameter((Object)cobblemonImplementation, (String)"<set-?>");
        implementation = cobblemonImplementation;
    }

    @NotNull
    public final CaptureCalculator getCaptureCalculator() {
        return this.getConfig().getCaptureCalculator();
    }

    public final void setCaptureCalculator(@NotNull CaptureCalculator value2) {
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.getConfig().setCaptureCalculator(value2);
    }

    @Deprecated(message="This field is now a config value", replaceWith=@ReplaceWith(expression="Cobblemon.config.captureCalculator", imports={}))
    public static /* synthetic */ void getCaptureCalculator$annotations() {
    }

    @NotNull
    public final ExperienceCalculator getExperienceCalculator() {
        return experienceCalculator;
    }

    public final void setExperienceCalculator(@NotNull ExperienceCalculator experienceCalculator) {
        Intrinsics.checkNotNullParameter((Object)experienceCalculator, (String)"<set-?>");
        Cobblemon.experienceCalculator = experienceCalculator;
    }

    @NotNull
    public final EvCalculator getEvYieldCalculator() {
        return evYieldCalculator;
    }

    public final void setEvYieldCalculator(@NotNull EvCalculator evCalculator) {
        Intrinsics.checkNotNullParameter((Object)evCalculator, (String)"<set-?>");
        evYieldCalculator = evCalculator;
    }

    @NotNull
    public final StarterHandler getStarterHandler() {
        return starterHandler;
    }

    public final void setStarterHandler(@NotNull StarterHandler starterHandler) {
        Intrinsics.checkNotNullParameter((Object)starterHandler, (String)"<set-?>");
        Cobblemon.starterHandler = starterHandler;
    }

    public final boolean isDedicatedServer() {
        return isDedicatedServer;
    }

    public final void setDedicatedServer(boolean bl) {
        isDedicatedServer = bl;
    }

    @NotNull
    public final ShowdownThread getShowdownThread() {
        return showdownThread;
    }

    @NotNull
    public final CobblemonConfig getConfig() {
        CobblemonConfig cobblemonConfig = config;
        if (cobblemonConfig != null) {
            return cobblemonConfig;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"config");
        return null;
    }

    public final void setConfig(@NotNull CobblemonConfig cobblemonConfig) {
        Intrinsics.checkNotNullParameter((Object)cobblemonConfig, (String)"<set-?>");
        config = cobblemonConfig;
    }

    @NotNull
    public final SpawningProspector getProspector() {
        return prospector;
    }

    public final void setProspector(@NotNull SpawningProspector spawningProspector) {
        Intrinsics.checkNotNullParameter((Object)spawningProspector, (String)"<set-?>");
        prospector = spawningProspector;
    }

    @NotNull
    public final AreaContextResolver getAreaContextResolver() {
        return areaContextResolver;
    }

    public final void setAreaContextResolver(@NotNull AreaContextResolver areaContextResolver2) {
        Intrinsics.checkNotNullParameter((Object)areaContextResolver2, (String)"<set-?>");
        areaContextResolver = areaContextResolver2;
    }

    @NotNull
    public final BestSpawner getBestSpawner() {
        return bestSpawner;
    }

    @NotNull
    public final BattleRegistry getBattleRegistry() {
        return battleRegistry;
    }

    @NotNull
    public final PokemonStoreManager getStorage() {
        return storage;
    }

    public final void setStorage(@NotNull PokemonStoreManager pokemonStoreManager) {
        Intrinsics.checkNotNullParameter((Object)pokemonStoreManager, (String)"<set-?>");
        storage = pokemonStoreManager;
    }

    @NotNull
    public final NbtMoLangDataStoreFactory getMolangData() {
        return molangData;
    }

    public final void setMolangData(@NotNull NbtMoLangDataStoreFactory nbtMoLangDataStoreFactory) {
        Intrinsics.checkNotNullParameter((Object)nbtMoLangDataStoreFactory, (String)"<set-?>");
        molangData = nbtMoLangDataStoreFactory;
    }

    @NotNull
    public final PlayerDataStoreManager getPlayerData() {
        PlayerDataStoreManager playerDataStoreManager = playerData;
        if (playerDataStoreManager != null) {
            return playerDataStoreManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"playerData");
        return null;
    }

    public final void setPlayerData(@NotNull PlayerDataStoreManager playerDataStoreManager) {
        Intrinsics.checkNotNullParameter((Object)playerDataStoreManager, (String)"<set-?>");
        playerData = playerDataStoreManager;
    }

    @NotNull
    public final StarterConfig getStarterConfig() {
        StarterConfig starterConfig = Cobblemon.starterConfig;
        if (starterConfig != null) {
            return starterConfig;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"starterConfig");
        return null;
    }

    public final void setStarterConfig(@NotNull StarterConfig starterConfig) {
        Intrinsics.checkNotNullParameter((Object)starterConfig, (String)"<set-?>");
        Cobblemon.starterConfig = starterConfig;
    }

    @NotNull
    public final DataProvider getDataProvider() {
        return dataProvider;
    }

    @NotNull
    public final PermissionValidator getPermissionValidator() {
        return (PermissionValidator)permissionValidator$delegate.getValue((Object)this, $$delegatedProperties[0]);
    }

    public final void setPermissionValidator(@NotNull PermissionValidator permissionValidator) {
        Intrinsics.checkNotNullParameter((Object)permissionValidator, (String)"<set-?>");
        permissionValidator$delegate.setValue((Object)this, $$delegatedProperties[0], (Object)permissionValidator);
    }

    @NotNull
    public final StatProvider getStatProvider() {
        return statProvider;
    }

    public final void setStatProvider(@NotNull StatProvider statProvider) {
        Intrinsics.checkNotNullParameter((Object)statProvider, (String)"<set-?>");
        Cobblemon.statProvider = statProvider;
    }

    @NotNull
    public final SeasonResolver getSeasonResolver() {
        return seasonResolver;
    }

    public final void setSeasonResolver(@NotNull SeasonResolver seasonResolver) {
        Intrinsics.checkNotNullParameter((Object)seasonResolver, (String)"<set-?>");
        Cobblemon.seasonResolver = seasonResolver;
    }

    public final void preInitialize(@NotNull CobblemonImplementation implementation) {
        Intrinsics.checkNotNullParameter((Object)implementation, (String)"implementation");
        this.setImplementation(implementation);
        LOGGER.info("Launching Cobblemon 1.5.2" + "" + " ");
        implementation.registerPermissionValidator();
        implementation.registerSoundEvents();
        implementation.registerBlocks();
        implementation.registerItems();
        implementation.registerEntityTypes();
        implementation.registerEntityAttributes();
        implementation.registerBlockEntityTypes();
        implementation.registerWorldGenFeatures();
        implementation.registerParticles();
        DropEntry.Companion.register$default(DropEntry.Companion, "command", CommandDropEntry.class, false, 4, null);
        DropEntry.Companion.register("item", ItemDropEntry.class, true);
        ExperienceGroups.INSTANCE.registerDefaults();
        CaptureCalculators.INSTANCE.registerDefaults$common();
        this.loadConfig();
        CobblemonOrePlacedFeatures.INSTANCE.register();
        CobblemonPlacedFeatures.INSTANCE.register();
        this.registerArgumentTypes();
        ShoulderEffectRegistry.INSTANCE.register$common();
        Observable.DefaultImpls.subscribe$default(CobblemonEvents.DATA_SYNCHRONIZED, null, (Function1)new Function1<ServerPlayer, Unit>(this){
            final /* synthetic */ Cobblemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayer it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Cobblemon.INSTANCE.getStorage().onPlayerDataSync(it);
                Cobblemon.INSTANCE.getPlayerData().get((Player)it).sendToPlayer(it);
                Cobblemon.INSTANCE.getStarterHandler().handleJoin(it);
                new ServerSettingsPacket(this.this$0.getConfig().getPreventCompletePartyDeposit(), this.this$0.getConfig().getDisplayEntityLevelLabel()).sendToPlayer(it);
            }
        }, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, preInitialize.2.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.PLAYER_DEATH, null, preInitialize.3.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.RIGHT_CLICK_ENTITY, null, preInitialize.4.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.RIGHT_CLICK_BLOCK, null, preInitialize.5.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.RIGHT_CLICK_BLOCK, null, preInitialize.6.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.CHANGE_DIMENSION, null, preInitialize.7.INSTANCE, 1, null);
        EntityDataSerializers.m_135050_((EntityDataSerializer)Vec3DataSerializer.INSTANCE);
        EntityDataSerializers.m_135050_((EntityDataSerializer)StringSetDataSerializer.INSTANCE);
        EntityDataSerializers.m_135050_((EntityDataSerializer)PoseTypeDataSerializer.INSTANCE);
        EntityDataSerializers.m_135050_((EntityDataSerializer)IdentifierDataSerializer.INSTANCE);
        CobblemonEvents.FRIENDSHIP_UPDATED.subscribe(Priority.LOWEST, (Function1<FriendshipUpdatedEvent, Unit>)((Function1)preInitialize.8.INSTANCE));
        HeldItemProvider.INSTANCE.register(CobblemonHeldItemManager.INSTANCE, Priority.LOWEST);
    }

    public final void initialize() {
        showdownThread.launch();
        CobblemonDataProvider.INSTANCE.registerDefaults();
        PokemonAspectsKt.getSHINY_ASPECT().register();
        PokemonAspectsKt.getGENDER_ASPECT().register();
        SpeciesFeatures.INSTANCE.getTypes().put("choice", ChoiceSpeciesFeatureProvider.class);
        SpeciesFeatures.INSTANCE.getTypes().put("flag", FlagSpeciesFeatureProvider.class);
        SpeciesFeatures.INSTANCE.getTypes().put("integer", IntSpeciesFeatureProvider.class);
        SpeciesFeatures.INSTANCE.register("milkable", new FlagSpeciesFeatureProvider(CollectionsKt.listOf((Object)"milkable"), true));
        SpeciesFeatures.INSTANCE.register("sheared", new FlagSpeciesFeatureProvider(CollectionsKt.listOf((Object)"sheared"), false));
        CustomPokemonProperty.Companion.register(UncatchableProperty.INSTANCE);
        CustomPokemonProperty.Companion.register(PokemonFlagProperty.INSTANCE);
        CustomPokemonProperty.Companion.register(HiddenAbilityPropertyType.INSTANCE);
        DistributionUtilsKt.ifDedicatedServer(Cobblemon::initialize$lambda$2);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_POST, null, initialize.2.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_PRE, null, initialize.3.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STARTING, null, initialize.4.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STOPPED, null, initialize.5.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STARTED, null, initialize.6.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_POST, null, initialize.7.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(CobblemonEvents.POKEMON_CAPTURED, null, initialize.8.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(CobblemonEvents.BATTLE_VICTORY, null, initialize.9.INSTANCE, 1, null);
        CobblemonEvents.EVOLUTION_COMPLETE.subscribe(Priority.LOWEST, (Function1<EvolutionCompleteEvent, Unit>)((Function1)new Function1<EvolutionCompleteEvent, Unit>(this){
            final /* synthetic */ Cobblemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull EvolutionCompleteEvent event) {
                Intrinsics.checkNotNullParameter((Object)event, (String)"event");
                AdvancementHandler.INSTANCE.onEvolve(event);
                Pokemon pokemon = event.getPokemon();
                ResourceLocation ninjaskIdentifier = MiscUtils.cobblemonResource("ninjask");
                if (this.this$0.getConfig().getNinjaskCreatesShedinja() && Intrinsics.areEqual((Object)pokemon.getSpecies().getResourceIdentifier(), (Object)ninjaskIdentifier) && PokemonSpecies.INSTANCE.getByIdentifier(Pokemon.Companion.getSHEDINJA$common()) != null) {
                    ServerPlayer serverPlayer = pokemon.getOwnerPlayer();
                    if (serverPlayer == null) {
                        return;
                    }
                    ServerPlayer player = serverPlayer;
                    if (player.m_7500_() || player.m_150109_().m_216874_(initialize.10::invoke$lambda$0)) {
                        Item pokeball = null;
                        pokeball = Items.f_41852_;
                        List list = player.m_150109_().f_35979_;
                        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"player.inventory.combinedInventory");
                        Iterable $this$forEach$iv = list;
                        boolean $i$f$forEach = false;
                        for (T element$iv : $this$forEach$iv) {
                            NonNullList it = (NonNullList)element$iv;
                            boolean bl = false;
                            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                            Iterable $this$forEach$iv2 = (Iterable)it;
                            boolean $i$f$forEach2 = false;
                            for (T element$iv2 : $this$forEach$iv2) {
                                ItemStack itemStack = (ItemStack)element$iv2;
                                boolean bl2 = false;
                                if (!(itemStack.m_41720_() instanceof PokeBallItem) || !Intrinsics.areEqual((Object)pokeball, (Object)Items.f_41852_)) continue;
                                Item item = itemStack.m_41720_();
                                Intrinsics.checkNotNull((Object)item, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem");
                                pokeball = (PokeBallItem)item;
                            }
                        }
                        if (!player.m_7500_()) {
                            Inventory inventory = player.m_150109_();
                            Intrinsics.checkNotNullExpressionValue((Object)inventory, (String)"player.inventory");
                            PlayerInventoryExtensionsKt.removeAmountIf(inventory, 1, initialize.10::invoke$lambda$3);
                        }
                        if (Intrinsics.areEqual((Object)pokeball, (Object)Items.f_41852_)) {
                            pokeball = CobblemonItems.POKE_BALL;
                        }
                        PokemonProperties properties2 = event.getEvolution().getResult().copy();
                        properties2.setSpecies(Pokemon.Companion.getSHEDINJA$common().toString());
                        Pokemon product = Pokemon.clone$default(pokemon, false, false, 3, null);
                        product.removeHeldItem();
                        properties2.apply(product);
                        Item item = pokeball;
                        Intrinsics.checkNotNull((Object)item, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem");
                        product.setCaughtBall(((PokeBallItem)item).getPokeBall());
                        StoreCoordinates<?> storeCoordinates = pokemon.getStoreCoordinates().get();
                        if (storeCoordinates != null && (storeCoordinates = storeCoordinates.getStore()) != null) {
                            ((PokemonStore)((Object)storeCoordinates)).add(product);
                        }
                        SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getEVOLVE_POKEMON();
                        PreEvolution preEvolution = event.getPokemon().getPreEvolution();
                        Intrinsics.checkNotNull((Object)preEvolution);
                        simpleCriterionTrigger.trigger(player, new EvolvePokemonContext(preEvolution.getSpecies().getResourceIdentifier(), product.getSpecies().getResourceIdentifier(), Cobblemon.INSTANCE.getPlayerData().get((Player)player).getAdvancementData().getTotalEvolvedCount()));
                    }
                }
            }

            private static final boolean invoke$lambda$0(ItemStack it) {
                return it.m_41720_() instanceof PokeBallItem;
            }

            private static final boolean invoke$lambda$3(ItemStack it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return it.m_41720_() instanceof PokeBallItem;
            }
        }));
        Observable.DefaultImpls.subscribe$default(CobblemonEvents.LEVEL_UP_EVENT, null, initialize.11.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(CobblemonEvents.TRADE_COMPLETED, null, initialize.12.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(BagItems.INSTANCE.getObservable(), null, initialize.13.INSTANCE, 1, null);
    }

    @Nullable
    public final Level getLevel(@NotNull ResourceKey<Level> dimension) {
        MinecraftServer minecraftServer;
        Intrinsics.checkNotNullParameter(dimension, (String)"dimension");
        if (isDedicatedServer) {
            minecraftServer = DistributionUtilsKt.server();
        } else {
            Minecraft mc = Minecraft.m_91087_();
            IntegratedServer integratedServer = mc.m_91092_();
            return integratedServer != null && (integratedServer = integratedServer.m_129880_(dimension)) != null ? (Level)integratedServer : (Level)mc.f_91073_;
        }
        return (Level)(minecraftServer != null ? minecraftServer.m_129880_(dimension) : null);
    }

    public final void loadConfig() {
        File configFile = new File(CONFIG_PATH);
        configFile.getParentFile().mkdirs();
        if (configFile.exists()) {
            try {
                FileReader fileReader = new FileReader(configFile);
                Object object = CobblemonConfig.Companion.getGSON().fromJson((Reader)fileReader, CobblemonConfig.class);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"CobblemonConfig.GSON.fro\u2026blemonConfig::class.java)");
                this.setConfig((CobblemonConfig)object);
                fileReader.close();
            }
            catch (Exception exception) {
                LOGGER.error("Failed to load the config! Using default config until the following has been addressed:");
                this.setConfig(new CobblemonConfig());
                exception.printStackTrace();
            }
            CobblemonConfig defaultConfig = new CobblemonConfig();
            Iterable $this$forEach$iv = KClasses.getMemberProperties((KClass)Reflection.getOrCreateKotlinClass(CobblemonConfig.class));
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Field field;
                KProperty1 it = (KProperty1)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNull((Object)ReflectJvmMapping.getJavaField((KProperty)((KProperty)it)));
                KCallablesJvm.setAccessible((KCallable)((KCallable)it), (boolean)true);
                Annotation[] annotationArray = field.getAnnotations();
                Intrinsics.checkNotNullExpressionValue((Object)annotationArray, (String)"field.annotations");
                Object[] $this$forEach$iv2 = annotationArray;
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    Object value2;
                    Annotation it2 = (Annotation)element$iv2;
                    boolean bl2 = false;
                    Annotation annotation = it2;
                    if (annotation instanceof LastChangedVersion) {
                        String lastSavedVersion;
                        String defaultChangedVersion = ((LastChangedVersion)it2).version();
                        if (!StringExtensionsKt.isLaterVersion(defaultChangedVersion, lastSavedVersion = INSTANCE.getConfig().getLastSavedVersion())) continue;
                        field.set(INSTANCE.getConfig(), field.get(defaultConfig));
                        continue;
                    }
                    if (!(annotation instanceof IntConstraint) || !((value2 = field.get(INSTANCE.getConfig())) instanceof Integer)) continue;
                    value2 = RangesKt.coerceIn((int)((Number)value2).intValue(), (int)((IntConstraint)it2).min(), (int)((IntConstraint)it2).max());
                    field.set(INSTANCE.getConfig(), value2);
                }
            }
        } else {
            this.setConfig(new CobblemonConfig());
        }
        this.getConfig().setLastSavedVersion(VERSION);
        this.saveConfig();
        bestSpawner.loadConfig();
        Observable.DefaultImpls.subscribe$default(PokemonSpecies.INSTANCE.getObservable(), null, (Function1)new Function1<PokemonSpecies, Unit>(this){
            final /* synthetic */ Cobblemon this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull PokemonSpecies it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                Cobblemon.INSTANCE.setStarterConfig(this.this$0.loadStarterConfig());
            }
        }, 1, null);
    }

    @NotNull
    public final StarterConfig loadStarterConfig() {
        if (this.getConfig().getExportStarterConfig()) {
            File file = new File("config/cobblemon/starters.json");
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                StarterConfig config = new StarterConfig();
                PrintWriter pw = new PrintWriter(file);
                StarterConfig.Companion.getGSON().toJson((Object)config, (Appendable)pw);
                pw.close();
                return config;
            }
            FileReader reader = new FileReader(file);
            StarterConfig config = (StarterConfig)StarterConfig.Companion.getGSON().fromJson((Reader)reader, StarterConfig.class);
            reader.close();
            Intrinsics.checkNotNullExpressionValue((Object)config, (String)"config");
            return config;
        }
        return new StarterConfig();
    }

    public final void saveConfig() {
        try {
            File configFile = new File(CONFIG_PATH);
            FileWriter fileWriter = new FileWriter(configFile);
            CobblemonConfig.Companion.getGSON().toJson((Object)this.getConfig(), (Appendable)fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception exception) {
            LOGGER.error("Failed to save the config! Please consult the following stack trace:");
            exception.printStackTrace();
        }
    }

    private final void registerArgumentTypes() {
        CobblemonImplementation cobblemonImplementation = this.getImplementation();
        ResourceLocation resourceLocation = MiscUtils.cobblemonResource("pokemon");
        KClass kClass = Reflection.getOrCreateKotlinClass(PokemonArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo = SingletonArgumentInfo.m_235451_(PokemonArgumentType.Companion::pokemon);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo, (String)"of(PokemonArgumentType::pokemon)");
        cobblemonImplementation.registerCommandArgument(resourceLocation, kClass, (ArgumentTypeInfo)singletonArgumentInfo);
        CobblemonImplementation cobblemonImplementation2 = this.getImplementation();
        ResourceLocation resourceLocation2 = MiscUtils.cobblemonResource("pokemon_properties");
        KClass kClass2 = Reflection.getOrCreateKotlinClass(PokemonPropertiesArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo2 = SingletonArgumentInfo.m_235451_(PokemonPropertiesArgumentType.Companion::properties);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo2, (String)"of(PokemonPropertiesArgumentType::properties)");
        cobblemonImplementation2.registerCommandArgument(resourceLocation2, kClass2, (ArgumentTypeInfo)singletonArgumentInfo2);
        CobblemonImplementation cobblemonImplementation3 = this.getImplementation();
        ResourceLocation resourceLocation3 = MiscUtils.cobblemonResource("spawn_bucket");
        KClass kClass3 = Reflection.getOrCreateKotlinClass(SpawnBucketArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo3 = SingletonArgumentInfo.m_235451_(SpawnBucketArgumentType.Companion::spawnBucket);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo3, (String)"of(SpawnBucketArgumentType::spawnBucket)");
        cobblemonImplementation3.registerCommandArgument(resourceLocation3, kClass3, (ArgumentTypeInfo)singletonArgumentInfo3);
        CobblemonImplementation cobblemonImplementation4 = this.getImplementation();
        ResourceLocation resourceLocation4 = MiscUtils.cobblemonResource("move");
        KClass kClass4 = Reflection.getOrCreateKotlinClass(MoveArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo4 = SingletonArgumentInfo.m_235451_(MoveArgumentType.Companion::move);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo4, (String)"of(MoveArgumentType::move)");
        cobblemonImplementation4.registerCommandArgument(resourceLocation4, kClass4, (ArgumentTypeInfo)singletonArgumentInfo4);
        CobblemonImplementation cobblemonImplementation5 = this.getImplementation();
        ResourceLocation resourceLocation5 = MiscUtils.cobblemonResource("party_slot");
        KClass kClass5 = Reflection.getOrCreateKotlinClass(PartySlotArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo5 = SingletonArgumentInfo.m_235451_(PartySlotArgumentType.Companion::partySlot);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo5, (String)"of(PartySlotArgumentType::partySlot)");
        cobblemonImplementation5.registerCommandArgument(resourceLocation5, kClass5, (ArgumentTypeInfo)singletonArgumentInfo5);
        CobblemonImplementation cobblemonImplementation6 = this.getImplementation();
        ResourceLocation resourceLocation6 = MiscUtils.cobblemonResource("pokemon_store");
        KClass kClass6 = Reflection.getOrCreateKotlinClass(PokemonStoreArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo6 = SingletonArgumentInfo.m_235451_(PokemonStoreArgumentType.Companion::pokemonStore);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo6, (String)"of(PokemonStoreArgumentType::pokemonStore)");
        cobblemonImplementation6.registerCommandArgument(resourceLocation6, kClass6, (ArgumentTypeInfo)singletonArgumentInfo6);
        CobblemonImplementation cobblemonImplementation7 = this.getImplementation();
        ResourceLocation resourceLocation7 = MiscUtils.cobblemonResource("dialogue");
        KClass kClass7 = Reflection.getOrCreateKotlinClass(DialogueArgumentType.class);
        SingletonArgumentInfo singletonArgumentInfo7 = SingletonArgumentInfo.m_235451_(DialogueArgumentType.Companion::dialogue);
        Intrinsics.checkNotNullExpressionValue((Object)singletonArgumentInfo7, (String)"of(DialogueArgumentType::dialogue)");
        cobblemonImplementation7.registerCommandArgument(resourceLocation7, kClass7, (ArgumentTypeInfo)singletonArgumentInfo7);
    }

    private static final void initialize$lambda$2() {
        isDedicatedServer = true;
    }

    static {
        LaxPermissionValidator laxPermissionValidator;
        Delegates delegates = new Delegates[]{Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Cobblemon.class, "permissionValidator", "getPermissionValidator()Lcom/cobblemon/mod/common/api/permission/PermissionValidator;", 0)))};
        $$delegatedProperties = delegates;
        INSTANCE = new Cobblemon();
        Logger logger = LogManager.getLogger();
        Intrinsics.checkNotNullExpressionValue((Object)logger, (String)"getLogger()");
        LOGGER = logger;
        experienceCalculator = StandardExperienceCalculator.INSTANCE;
        evYieldCalculator = Generation8EvCalculator.INSTANCE;
        starterHandler = new CobblemonStarterHandler();
        showdownThread = new ShowdownThread();
        prospector = CobblemonSpawningProspector.INSTANCE;
        areaContextResolver = new AreaContextResolver(){

            @NotNull
            public List<AreaSpawningContext> resolve(@NotNull Spawner spawner, @NotNull List<? extends AreaSpawningContextCalculator<?>> contextCalculators, @NotNull WorldSlice slice) {
                return AreaContextResolver.DefaultImpls.resolve(this, spawner, contextCalculators, slice);
            }
        };
        bestSpawner = BestSpawner.INSTANCE;
        battleRegistry = BattleRegistry.INSTANCE;
        storage = new PokemonStoreManager();
        molangData = NbtMoLangDataStoreFactory.INSTANCE;
        dataProvider = CobblemonDataProvider.INSTANCE;
        delegates = Delegates.INSTANCE;
        LaxPermissionValidator it = laxPermissionValidator = new LaxPermissionValidator();
        boolean bl = false;
        it.initialize();
        LaxPermissionValidator initialValue$iv = laxPermissionValidator;
        boolean $i$f$observable = false;
        permissionValidator$delegate = (ReadWriteProperty)new ObservableProperty<PermissionValidator>((Object)initialValue$iv){

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void newValue2;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                PermissionValidator permissionValidator = (PermissionValidator)newValue;
                PermissionValidator cfr_ignored_0 = (PermissionValidator)oldValue;
                boolean bl = false;
                newValue2.initialize();
            }
        };
        statProvider = CobblemonStatProvider.INSTANCE;
        seasonResolver = TagSeasonResolver.INSTANCE;
    }
}

